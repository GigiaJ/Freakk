package main;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.*;

public class JNA {

	public interface User32 extends StdCallLibrary, WinUser, WinNT {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		HWND GetForegroundWindow(); // add this

		boolean RegisterHotKey(HWND hWnd, int id, int fsModifiers, int vk);

		boolean UnregisterHotkey();

		int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);

		boolean GetGUIThreadInfo(int i, WinUser.GUITHREADINFO g);

		boolean GetWindowRect(PointerType hwnd, RECT bounds);

		int GetWindowThreadProcessId(HWND hwnd, IntByReference pId);

		HWND FindWindow(String lpClassName, String lpWindowName);

		boolean AttachThreadInput(int idAttach, int idAttachTo, boolean fAttach); //Originally the ints are DWORD's but because DWORD is just the byte size and it matches integer sizes this works fine
																				//Could be dangerous and have unintended side effects if used improperly though
		
		WinDef.HWND SetFocus(WinDef.HWND hWnd);
		boolean GetCaretPos (POINT lpPoint);
		
		
	}

	public static String currentWindow() throws InterruptedException {
		byte[] windowText = new byte[512];
		PointerType hwnd = User32.INSTANCE.GetForegroundWindow(); // then you can call it!
		User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
		return Native.toString(windowText);
	}

	public static String currentBounds() {
		PointerType hwnd = User32.INSTANCE.GetForegroundWindow(); // then you can call it!
		RECT bounds = new RECT();
		User32.INSTANCE.GetWindowRect(hwnd, bounds);

		return bounds.toString();
	}


}