package com.arknights.priestess.core;

import com.arknights.priestess.tools.Image_FindOffset;
import com.arknights.priestess.tools.Tools;

import java.io.IOException;
import java.util.Map;

public class Game_SceneIden {
    /**
     * 场景判断
     * @return 场景名
     * @throws IOException IO异常
     */
    public static String sceneIden() throws IOException {
        ADB_Screenshot.ADBScreenshot();

        Map<String, String> sceneMap = System_ConfigHandler.getAllProperties("asset.properties");

        for (Map.Entry<String, String> entry : sceneMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.startsWith("ui_")){
                // 多点判断
                if (value.contains(";")){
                    if (sceneIden_multipoints(value.split(";"))){
                        return key;
                    }
                }
                // 分支判断
                else if (value.contains("|")){
                    if (sceneIden_branch(value.split("\\|"))){
                        return key;
                    }
                }
                // 普通判断
                else {
                    if (Image_FindOffset.find(Tools.getImgMat(System_ConfigHandler.getProperty("config.properties", "screenshot_full_path")), Tools.getImgMat(value)).getSimilarity() >= 0.9){
                        return key;
                    }
                }
            }
        }
        return "Unknown";
    }

    /**
     * 多点判断
     * @param points 点位
     * @return 是否符合
     * @throws IOException IO异常
     */
    public static boolean sceneIden_multipoints(String[] points) throws IOException {
        for (String point : points) {
            if (Image_FindOffset.find(Tools.getImgMat(System_ConfigHandler.getProperty("config.properties", "screenshot_full_path")), Tools.getImgMat(point)).getSimilarity() < 0.9){
                return false;
            }
        }
        return true;
    }

    /**
     * 分支判断
     * @param points 点位
     * @return 是否符合
     * @throws IOException IO异常
     */
    public static boolean sceneIden_branch(String[] points) throws IOException {
        for (String point : points) {
            if (Image_FindOffset.find(Tools.getImgMat(System_ConfigHandler.getProperty("config.properties", "screenshot_full_path")), Tools.getImgMat(point)).getSimilarity() >= 0.9){
                return true;
            }
        }
        return false;
    }
}
