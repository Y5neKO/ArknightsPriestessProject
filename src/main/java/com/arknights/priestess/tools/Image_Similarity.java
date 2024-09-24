package com.arknights.priestess.tools;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class Image_Similarity {
    /**
     * 直方图法
     * @param image1path 图片1路径
     * @param image2path 图片2路径
     */
    public static double compare(String image1path, String image2path) {
        // 载入OpenCV库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 加载图片
        Mat image1 = Imgcodecs.imread(image1path);
        Mat image2 = Imgcodecs.imread(image2path);

        // 处理为相同大小
        Imgproc.resize(image1, image1, image2.size());
        Imgproc.resize(image2, image2, image1.size());

        // 分别计算两张图的直方图
        Mat hist1 = calculateHistogram(image1);
        Mat hist2 = calculateHistogram(image2);

        final double similarity = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL);

        hist1.release();
        hist2.release();

        System.out.println("直方图法：" + similarity);

        return similarity;
    }

    /**
     * 直方图计算
     * @param image 图片Mat对象
     * @return 直方图Mat对象
     */
    private static Mat calculateHistogram(Mat image) {
        Mat hist = new Mat();

        // 设置直方图参数
        MatOfInt histSize = new MatOfInt(256);
        MatOfFloat ranges = new MatOfFloat(0, 256);
        MatOfInt channels = new MatOfInt(0);
        List<Mat> images = new ArrayList<>();
        images.add(image);

        // 计算直方图
        Imgproc.calcHist(images, channels, new Mat(), hist, histSize, ranges);

        return hist;
    }
}
