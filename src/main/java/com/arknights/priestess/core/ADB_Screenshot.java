package com.arknights.priestess.core;

import java.io.IOException;

public class ADB_Screenshot {
    public static boolean DroidCast_raw() {
        return true;
    }

    /**
     * 使用adb截图
     * @return 是否截图成功
     * @throws IOException 异常
     */
    public static boolean ADBScreenshot() throws IOException {
        String[] commands = {
                System_ConfigHandler.getProperty("config.properties", "adb_path") + " shell screencap /sdcard/screenshot.png",
                System_ConfigHandler.getProperty("config.properties", "adb_path") + " pull /sdcard/screenshot.png " + System.getProperty("user.dir") + System_ConfigHandler.getProperty("config.properties", "screenshot_path") + "\\screenshot.png",
                System_ConfigHandler.getProperty("config.properties", "adb_path") + " shell rm /sdcard/screenshot.png"
        };
//        System.out.println(Arrays.toString(commands));
        System_Command system_command = new System_Command();
        system_command.runCommand(commands);
        return true;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        ADBScreenshot();
    }
}
