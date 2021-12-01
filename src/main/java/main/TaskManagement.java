/**
 * @author		GigiaJ
 * @filename	TaskManagement.java
 * @date		Mar 27, 2020
 * @description 
 */

package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TaskManagement {
    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /F /IM ";

    public TaskManagement() {
    }

    public static boolean isProcessRunning(String serviceName) throws Exception {
        Process p = Runtime.getRuntime().exec("tasklist");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        while((line = reader.readLine()) != null) {
            if (line.contains(serviceName)) {
                return true;
            }
        }

        return false;
    }

    public static void killProcess(String serviceName) throws Exception {
        Runtime.getRuntime().exec("taskkill /F /IM " + serviceName);
    }
}
