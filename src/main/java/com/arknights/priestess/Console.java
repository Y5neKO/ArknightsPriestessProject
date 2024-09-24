package com.arknights.priestess;

import com.arknights.priestess.copyright.Copyright;
import com.arknights.priestess.core.*;
import nu.pattern.OpenCV;
import org.opencv.core.Core;

public class Console {
    public static void main(String[] args) throws Exception {
        System.out.println(Copyright.getLogo());

        // 载入OpenCV库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadShared();

        String gameScene;

        // ====================STEP 1: 前期检查====================
        // ADB安装检查
        if(ADB_Main.adbCheck()){
            System.out.println(System_Log.buffer_logging("SUCC", "ADB install check SUCCESS"));
        } else {
            System.out.println(System_Log.buffer_logging("FAIL", "ADB install check FAIL"));
        }
        // ADB连接检查
        if(ADB_Main.adbLink()){
            ADB_Main.adbDevices();
        }
        // 截图方案检查
        System.out.println(System_Log.buffer_logging("INFO", "Screenshot type: " + System_ConfigHandler.getProperty("config.properties", "screenshot_type")));
        System.out.println(System_Log.buffer_logging("INFO", "Screenshot full path: " + System_ConfigHandler.getProperty("config.properties", "screenshot_full_path")));
        ADB_Main.adbCheckScreenshot();
        // ====================STEP 2: 场景判断====================

        gameScene = Game_SceneIden.sceneIden();
        System.out.println(System_Log.buffer_logging("INFO", "Current scene: " + gameScene));

        Game_Operate.backMain();
    }
}
