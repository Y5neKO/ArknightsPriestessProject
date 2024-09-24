package com.arknights.priestess.tools;

/**
 * 图片偏移对象
 * 包括: x坐标，y坐标，相似度
 */
public class Image_OffsetObject {
    private int x;
    private int y;
    private double similarity;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
