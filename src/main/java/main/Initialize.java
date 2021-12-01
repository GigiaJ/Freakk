/**
 * @author		GigiaJ
 * @filename	Initialize.java
 * @date		Mar 27, 2020
 * @description 
 */

package main;

import java.awt.AWTException;
import messages.GlobalListeners;

public class Initialize extends Thread {
    public Initialize() {
    }

    public synchronized void run() {
        try {
            GlobalListeners.mouseListener();
            GlobalListeners.keyboardListener();
            FileLoader.loadFiles();
        } catch (InterruptedException | AWTException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
