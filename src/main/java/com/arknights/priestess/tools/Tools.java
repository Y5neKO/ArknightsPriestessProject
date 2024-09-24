package com.arknights.priestess.tools;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.HashMap;
import java.util.Map;

public class Tools {
    public static final String BLACK = "\033[30m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String PURPLE = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";
    public static final String RESET = "\033[0m";

    private static final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("BLACK", BLACK);
        COLOR_MAP.put("RED", RED);
        COLOR_MAP.put("GREEN", GREEN);
        COLOR_MAP.put("YELLOW", YELLOW);
        COLOR_MAP.put("BLUE", BLUE);
        COLOR_MAP.put("PURPLE", PURPLE);
        COLOR_MAP.put("CYAN", CYAN);
        COLOR_MAP.put("WHITE", WHITE);
    }

    /**
     * 改变字体颜色
     * @param str 字符串
     * @param color 颜色
     * @return 包含ANSI转义的字符串
     */
    public static String color(String str, String color){
        String colorCode = COLOR_MAP.getOrDefault(color, RESET);
        return colorCode + str + RESET;
    }

    /**
     * 打印分隔符
     */
    public static void printSeparator() {
        System.out.println("----------------------------------------");
    }

    public static Mat getImgMat(String imgPath){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadShared();

        return Imgcodecs.imread(imgPath);
    }
}
