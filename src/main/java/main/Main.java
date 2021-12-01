package main;

import bot.settings.Setting;

import java.awt.AWTException;
import java.io.File;

import messages.GlobalListeners;

public class Main {
    public static File baseFolder;
    public static File browserCookiesFolder;
    public static File settingsFile;
    public static File imageFile;
    protected static File tokenLocation;
    public static String userDirectory;
    public static Setting settings;

    public Main() {
    }

    public static void main(String[] args) throws Exception {
        StartUp.startUp();
        initialize();
    }

    private static synchronized void initialize() throws Exception {
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
