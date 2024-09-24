package com.arknights.priestess.core;

import com.arknights.priestess.tools.Tools;

import java.io.IOException;

public class ADB_Main {
    // ADB.exe路径
    private static final String adbPath = System.getProperty("user.dir") + "\\platform-tools\\adb.exe";

    // 模拟器进程名
    static String[] simulatorProcessNames = {
            "MuMuPlayer.exe"
    };

    // 截图方案
    static String[] screenshotTypes = {
            "DroidCast_raw",
            "ADB"
    };

    static String screenshotFullPath;

    static {
        try {
            screenshotFullPath = System.getProperty("user.dir") + System_ConfigHandler.getProperty("config.properties", "screenshot_path") + "\\screenshot.png";
            System_ConfigHandler.setProperty("config.properties", "screenshot_full_path", screenshotFullPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查ADB程序是否安装
     * @return 是否安装
     */
    public static boolean adbCheck() throws Exception {
        System.out.println(System_Log.buffer_logging("INFO", "Checking install ADB..."));

        String[] commands = {
                adbPath + " --version"
        };

        System_Command system_command = new System_Command();

        if(system_command.runCommand(commands)){
//            Tools.printSeparator();
//            System.out.println(system_command.getCommandResult(0));
            System_ConfigHandler.setProperty("config.properties", "adb_path", adbPath);
//            Tools.printSeparator();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 初始化adb连接模拟器
     * @return 是否连接成功
     */
    public static boolean adbLink() throws Exception {
        System.out.println(System_Log.buffer_logging("INFO", "Linking ADB..."));

        String simulatorPort = "16384";

        // 判断模拟器类型写入配置
        String[] commands_simulator = {
                adbPath + " kill-server",
                adbPath + " start-server",
                "tasklist"
        };
        System_Command system_command = new System_Command();
        system_command.runCommand(commands_simulator);
        for (String simulatorProcessName : simulatorProcessNames) {
            if (system_command.getCommandResult(0).contains(simulatorProcessName)) {
                System.out.println(System_Log.buffer_logging("INFO", simulatorProcessName + " is running"));
                System_ConfigHandler.setProperty("config.properties", "simulator_type", simulatorProcessName);
                break;
            }
            break;
        }

        // 判断端口
        if (system_command.getCommandResult(0).contains(simulatorProcessNames[0])) {
            simulatorPort = "16384";
        } else if (system_command.getCommandResult(0).contains(simulatorProcessNames[1])) {
            simulatorPort = "62001";
        } else if (system_command.getCommandResult(0).contains(simulatorProcessNames[2])) {
            simulatorPort = "21530";
        }

        // 开始adb连接
        String[] commands_link = {
                adbPath + " connect 127.0.0.1:" + simulatorPort
        };
        system_command.runCommand(commands_link);
        if (system_command.getCommandResult(0).contains("connected to ")) {
            System.out.println(System_Log.buffer_logging("SUCC", "ADB link SUCCESS"));

            System_ConfigHandler.setProperty("config.properties", "simulator_port", simulatorPort);

            return true;
        } else {
            System.out.println(System_Log.buffer_logging("FAIL", "ADB link FAIL"));
            return false;
        }
    }

    /**
     * 获取adb设备
     */
    public static void adbDevices() {
        System.out.println(System_Log.buffer_logging("INFO", "Checking ADB devices..."));
        String[] commands = {
                adbPath + " devices"
        };
        System_Command system_command = new System_Command();
        system_command.runCommand(commands);
        Tools.printSeparator();
        System.out.println(system_command.getCommandResult(0));
        Tools.printSeparator();
    }

    /**
     * 测试截图方案性能
     */
    public static void adbCheckScreenshot() throws IOException {
        System.out.println(System_Log.buffer_logging("INFO", "Checking screenshot..."));
        long startTime = System.currentTimeMillis();
        if (!ADB_Screenshot.ADBScreenshot()){
            System.out.println(System_Log.buffer_logging("FAIL", "Screenshot FAIL"));
            return;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(System_Log.buffer_logging("INFO", "Screenshot time: " + (endTime - startTime) + "ms"));
    }

    /**
     * 生成点击命令
     * @param x x坐标
     * @param y y坐标
     * @return 命令
     * @throws IOException IO异常
     */
    public static String click(int x, int y) throws IOException {
        return System_ConfigHandler.getProperty("config.properties", "adb_path") + " shell input tap " + x + " " + y;
    }

    /**
     * 生成滑动命令
     * @param x1 开始的x坐标
     * @param y1 开始的y坐标
     * @param x2 结束的x坐标
     * @param y2 结束的y坐标
     * @param duration 滑动时间
     * @return 命令
     * @throws IOException IO异常
     */
    public static String swipe(int x1, int y1, int x2, int y2, int duration) throws IOException {
        return System_ConfigHandler.getProperty("config.properties", "adb_path") + " shell input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + duration;
    }

    public static void main(String[] args) throws Exception {
        if(adbCheck()){
            System.out.println(System_Log.buffer_logging("SUCC", "ADB install check SUCCESS"));
        } else {
            System.out.println(System_Log.buffer_logging("FAIL", "ADB install check FAIL"));
        }

        if (adbLink()) {
            adbDevices();
        }

        System.out.println(System_Log.buffer_logging("INFO", "Screenshot type: " + System_ConfigHandler.getProperty("config.properties", "screenshot_type")));
        System.out.println(System_Log.buffer_logging("INFO", "Screenshot full path: " + screenshotFullPath));
        adbCheckScreenshot();
    }
}
