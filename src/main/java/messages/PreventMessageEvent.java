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

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import main.JNA;
import main.TextTransfer;

public class PreventMessageEvent {
    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
    private static User32 lib;
    public static boolean messageDeleted = true;
    private static boolean second = false;
    
    public PreventMessageEvent() {
    }

    public static void createBlockHook() {
         PreventMessageEvent.lib = User32.INSTANCE;
         HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle((String)null);
         PreventMessageEvent.keyboardHook = new LowLevelKeyboardProc() {
             public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
                 if (nCode >= 0) {
                     if (PreventMessageEvent.isDiscord()) {
						    TextTransfer textTransfer = new TextTransfer();
						    switch(info.vkCode) {
						    case 13:
						        if (GlobalListeners.isWithinMessageBox && !GlobalListeners.autoCompleteMenuOpen) {
						            try {
						                Robot robot = new Robot();
						    	        robot.keyPress(KeyEvent.VK_CONTROL);
						    	        robot.keyPress(KeyEvent.VK_A);
						    	        robot.keyRelease(KeyEvent.VK_CONTROL);
						    	        robot.keyRelease(KeyEvent.VK_A);
						    	        robot.keyPress(KeyEvent.VK_CONTROL);
						    	        robot.keyPress(KeyEvent.VK_X);
						    	        robot.keyRelease(KeyEvent.VK_CONTROL);
						    	        robot.keyRelease(KeyEvent.VK_X);
						            } catch (AWTException var7) {
						                var7.printStackTrace();
						            }

						            if (PreventMessageEvent.second) {
						                String lastTypedMessage = textTransfer.getClipboardContents();
						                if (lastTypedMessage != null && !lastTypedMessage.isEmpty() && lastTypedMessage != "") {
						                	MessageInfo.MessageCreated(lastTypedMessage);
						                    PreventMessageEvent.second = false;
						                    PreventMessageEvent.messageDeleted = false;
						                    textTransfer.setClipboardContents("");
						                    return new LRESULT(1L);
						                }
						            }

						            PreventMessageEvent.messageDeleted = false;
						            PreventMessageEvent.second = true;
						        }

						        return null;
						    }
						}
                 }

                 return PreventMessageEvent.lib.CallNextHookEx(PreventMessageEvent.hhk, nCode, wParam, new LPARAM(info.getPointer().getLong(0L)));
             }
         };
         PreventMessageEvent.hhk = PreventMessageEvent.lib.SetWindowsHookEx(13, PreventMessageEvent.keyboardHook, hMod, 0);
         MSG msg = new MSG();

         int result;
         while((result = PreventMessageEvent.lib.GetMessage(msg, (HWND)null, 0, 0)) != 0 && result != -1) {
             PreventMessageEvent.lib.TranslateMessage(msg);
             PreventMessageEvent.lib.DispatchMessage(msg);
         }

         PreventMessageEvent.lib.UnhookWindowsHookEx(PreventMessageEvent.hhk);
    }
    
    public static void blockEnterKey() {
        if (isWindows()) {
        	if (hhk == null) {
        		createBlockHook();
        	}
        }
    }

    @SuppressWarnings("deprecation")
	public static void unblockEnterKey() {
        if (isWindows() && lib != null) {
            lib.UnhookWindowsHookEx(hhk);
            hhk = null;
            messageDeleted = true;
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