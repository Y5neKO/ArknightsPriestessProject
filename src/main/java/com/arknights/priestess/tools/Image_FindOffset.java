package com.arknights.priestess.tools;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class Image_FindOffset {
    public static Image_OffsetObject find(Mat src, Mat dst){
        OpenCV.loadShared();

        // 进行模板匹配
        Mat output = new Mat(src.rows(), src.cols(), src.type());
        Imgproc.matchTemplate(src, dst, output, Imgproc.TM_CCOEFF_NORMED);

        // 获取匹配结果并查找最大匹配值
        Core.MinMaxLocResult result = Core.minMaxLoc(output);
        Point matchLoc = result.maxLoc;
        double matchVal = result.maxVal;
        int x = (int) matchLoc.x;
        int y = (int) matchLoc.y;
//        System.out.println(x + ", " + y + ", similarity: " + matchVal);
        Image_OffsetObject imageOffsetObject = new Image_OffsetObject();
        imageOffsetObject.setX(x);
        imageOffsetObject.setY(y);
        imageOffsetObject.setSimilarity(matchVal);
        return imageOffsetObject;
    }
}

