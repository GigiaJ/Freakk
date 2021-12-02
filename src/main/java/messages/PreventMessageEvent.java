package messages;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

import eventInfo.MessageInfo;

import handler.CommandHandler;
import main.ClipboardHandler;
import main.JNA;

import java.util.concurrent.*;

public class PreventMessageEvent {
    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
    private static HMODULE hMod;
    private static User32 lib;
    private static ExecutorService es;
    private static boolean isNotDuplicate = false;
    private static Future<?> blockTask;


    public static void setBlockTask(Future<?> future) {
        blockTask = future;
    }
    public static Future<?> getBlockTask() {
        return blockTask;
    }

    public static ExecutorService getExecutorService() {
        return es;
    }

    private static LRESULT hookCallback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
        if (nCode >= 0) {
            if (isDiscord() && isEnterPressed(info.vkCode)) {
                if (GlobalListeners.isWithinMessageBox && !GlobalListeners.autoCompleteMenuOpen ) {
                    ClipboardHandler.getInstance().copyToClipboard();
                    if (isNotDuplicate) {
                        String lastTypedMessage = ClipboardHandler.getInstance().getClipboardContents();
                        if (isClipboardEmptyAndIsUsable(lastTypedMessage)) {
                            setBlockTask(getExecutorService().submit(() -> {
                                MessageInfo.MessageCreated(lastTypedMessage);
                                while (MessageInfo.getMessage() == null);
                                    try {
                                        CommandHandler.handler();
                                        MessageInfo.message = null;
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }
                            }));
                            isNotDuplicate = false;
                            return new LRESULT(-1L);
                        }
                    }
                    isNotDuplicate = true;
                }
                return new LRESULT(1L);
            }
        }
        return PreventMessageEvent.lib.CallNextHookEx(PreventMessageEvent.hhk, nCode, wParam, new LPARAM(info.getPointer().getLong(0L)));
    }


    private static boolean isEnterPressed(int code) {
        return code == 13;
    }

    private static boolean isClipboardEmptyAndIsUsable(String clipboardContents) {
        return clipboardContents != null && !clipboardContents.isEmpty();
    }

    public static void createBlockHook() {
        if (hhk == null) {
            PreventMessageEvent.hhk = PreventMessageEvent.lib.SetWindowsHookEx(13, PreventMessageEvent.keyboardHook, hMod, 0);
            MSG msg = new MSG();
            long result;
            while ((result = PreventMessageEvent.lib.GetMessage(msg, (HWND) null, 0, 0)) != 0 && result != -1) {
                PreventMessageEvent.lib.TranslateMessage(msg);
                PreventMessageEvent.lib.DispatchMessage(msg);
            }
            PreventMessageEvent.lib.UnhookWindowsHookEx(PreventMessageEvent.hhk);
        }
    }

    public static void createLib() {
        if (lib == null) {
            PreventMessageEvent.lib = User32.INSTANCE;
            hMod = Kernel32.INSTANCE.GetModuleHandle((String) null);
            keyboardHook = PreventMessageEvent::hookCallback;
            es = Executors.newSingleThreadExecutor();
        }

    }

    public static void blockEnterKey() {
        if (isWindows()) {
            createLib();
            createBlockHook();
        }
    }

	public static void unblockEnterKey() {
        if (lib != null) {
            lib.UnhookWindowsHookEx(hhk);
            hhk = null;
            lib = null;
        }
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.indexOf("win") >= 0;
    }

    public static boolean isDiscord() {
        try {
            if (JNA.currentWindow().endsWith("- Discord")) {
                return true;
            }
        } catch (InterruptedException var1) {
            var1.printStackTrace();
        }

        return false;
    }
}