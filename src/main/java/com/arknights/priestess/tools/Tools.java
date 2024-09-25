package com.arknights.priestess.tools;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.FileWriter;
import java.io.IOException;
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

    public static String color_html(String str, String color){
        return "<span style=\"color: " + color + ";\">" + str + "</span>";
    }

    /**
     * 打印分隔符
     */
    public static void printSeparator() {
        System.out.println("----------------------------------------");
    }

    /**
     * 获取图片Mat
     * @param imgPath 图片路径
     * @return 图片Mat
     */
    public static Mat getImgMat(String imgPath){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        OpenCV.loadShared();

        return Imgcodecs.imread(imgPath);
    }

    /**
     * 获取图片按钮
     * @param imgPath 图片路径
     * @return 图片按钮
     */
    public static Button getImgButton(String imgPath) {
        Button button = new Button();
        Image imageClose = new Image(imgPath);
        ImageView imageViewClose = new ImageView(imageClose);
        imageViewClose.setFitHeight(20);
        imageViewClose.setPreserveRatio(true);
        button.setGraphic(imageViewClose);
        button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // 仅显示图形内容
        return button;
    }

    /**
     * 向文件追加内容
     * @param filePath 文件路径
     * @param content 内容
     */
    public static void fileAppend(String filePath, String content) {
        try (FileWriter fw = new FileWriter(filePath, true)) { // true 表示以追加模式打开
            fw.write(content); // 写入内容
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
