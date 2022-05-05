/**
 * 所有与图像识别有关的方法
 */

import net.sourceforge.tess4j.Tesseract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageIdentification {

    /**
     * 用于获取指定坐标的RGB量
     * @param ImagePath 图片路径
     * @param x 指定x坐标
    * @param y 指定y坐标
    * @return rgb RGB数组
    */
    public static int[] getImageRGB(String ImagePath,int x, int y) {
        int[] rgb = null;                   //初始化rgb值，用以应对读取失败的情况

        try{
            BufferedImage bfImage = ImageIO.read(new File(ImagePath));
            if (bfImage != null && x < bfImage.getWidth() && y < bfImage.getHeight()){
                rgb = new int[3];
                int pixel = bfImage.getRGB(x, y);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                Color color = new Color(rgb[0], rgb[1], rgb[2]);
                //依次获取RGB三个坐标
                rgb[0] = color.getRed();
                rgb[1] = color.getGreen();
                rgb[2] = color.getBlue();
            }
        }catch (IOException error){
            error.printStackTrace();
        }

        return rgb;
    }


    /**
     * 获取十六进制颜色代码,引用了getImageRGB方法
     * @param ImagePath 图片路径
     * @param x 指定x坐标
     * @param y 指定y坐标
     * @return 返回十六进制颜色代码，类型为String
     */
    public static String getImageRGB_hex(String ImagePath, int x, int y){
        String rgb_hex = null;
        int[] rgb = getImageRGB(ImagePath, x ,y);
        rgb_hex = "#" + toHexValue(rgb[0]) + toHexValue(rgb[1]) + toHexValue(rgb[2]);
        return rgb_hex;
    }
    private static String toHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
            builder.append("0");
        }
        return builder.toString().toUpperCase();
    }


    /**
     * 图像识别方法，引用了Tess4J库
     * @param imgPath 图片路径
     * @return 返回图像识别结果
     */
    public static String identification(String imgPath){
        try {
            File imageFile = new File(imgPath);         //读取图片并创建一个对象
            if (!imageFile.exists()){
                return "图片不存在";
            }
            BufferedImage bfImage = ImageIO.read(imageFile);        //创建一个BufferedImage来加载图片

            //引用Tess4J的内的方法
            Tesseract tesseract = new Tesseract();              //创建一个Tesseract对象，用来接受识图操作
            tesseract.setDatapath(System.getProperty("user.dir"));  //获取当前目录
            tesseract.setLanguage("chi_sim");                       //导入中文训练模型
            String result = null;                                   //初始化result变量接受识别结果
            result = tesseract.doOCR(bfImage);                      //调用ocr方法识别
            return result;

        }catch (Exception error){
            error.printStackTrace();
            ArknightsPriestess.errorLog(globalConfig.log_path + "log.txt", error.toString());
            return "图片识别出错";
        }
    }


    /**
     * 矩形裁剪，设定起始位置，裁剪宽度，裁剪长度
     * 裁剪范围需小于等于图像范围
     * @param image 原图片BufferedImage格式
     * @param xCoordinate 指定x起始坐标
     * @param yCoordinate 指定y起始坐标
     * @param xLength 指定延x轴裁剪的长度
     * @param yLength 指定延y轴裁剪的长度
     * @return 返回处理完成图片的BufferedImage格式
     */
    public static BufferedImage imageCutByRectangle(BufferedImage image, int xCoordinate, int yCoordinate, int xLength,
                                             int yLength) {
        //判断x、y方向是否超过图像最大范围
        if((xCoordinate + xLength) >= image.getWidth()) {
            xLength = image.getWidth() - xCoordinate;
        }
        if ((yCoordinate + yLength) >= image.getHeight()) {
            yLength = image.getHeight() - yCoordinate;
        }
        BufferedImage resultImage = new BufferedImage(xLength, yLength, image.getType());
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
                resultImage.setRGB(x, y, rgb);
            }
        }
        return resultImage;
    }

    /**
     * 圆形裁剪，定义圆心坐标，半径
     * 裁剪半径可以输入任意大于零的正整数
     * @param image 原图片BufferedImage格式
     * @param xCoordinate 指定x起始坐标
     * @param yCoordinate 指定y起始坐标
     * @param radius 指定圆形半径
     * @return 返回处理完成图片的BufferedImage格式
     */
    public static BufferedImage imageCutByCircle(BufferedImage image, int xCoordinate, int yCoordinate, int radius) {
        //判断圆心左右半径是否超限
        if ((xCoordinate + radius) > image.getWidth() || radius > xCoordinate) {
            int a = image.getWidth() - 1 - xCoordinate;
            if (a > xCoordinate) {
                radius = xCoordinate;
            }else {
                radius = a;
            }
        }
        //判断圆心上下半径是否超限
        if ((yCoordinate + radius) > image.getHeight() || radius >yCoordinate) {
            int a = image.getHeight() - 1 - yCoordinate;
            if (a > yCoordinate) {
                radius = yCoordinate;
            }else {
                radius = a;
            }
        }
        int length = 2 * radius + 1;
        BufferedImage resultImage = new BufferedImage(length, length, image.getType());
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int x = i - radius;
                int y = j - radius;
                int distance = (int) Math.sqrt(x * x + y * y);
                if (distance <= radius) {
                    int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
                    resultImage.setRGB(i, j, rgb);
                }
            }
        }
        return resultImage;
    }


    /**
     * 调用imageCutByRectangle处理需要矩形裁剪的图片
     * @param xCoordinate 指定x起始坐标
     * @param yCoordinate 指定y起始坐标
     * @param xLength 指定延x轴裁剪的长度
     * @param yLength 指定延y轴裁剪的长度
     * @return 返回一个flag标识以便确认是否处理成功
     */
    public static int imageCut_Rectangle(int xCoordinate, int yCoordinate, int xLength, int yLength) {
        int flag = 0;
        try{
            File input = new File(globalConfig.adbScreenshotName);
            File output = new File(globalConfig.adbScreenshot_path + "/screenshot_cut.png");
            BufferedImage image = ImageIO.read(input);
            BufferedImage result = imageCutByRectangle(image, xCoordinate, yCoordinate, xLength, yLength);
            ImageIO.write(result, "png", output);
        }catch (Exception error){
            error.printStackTrace();
        }
        return flag;
    }


    /**
     * 调用imageCutByCircle处理需要圆形裁剪的图片
     * @param xCoordinate 指定x起始坐标
     * @param yCoordinate 指定y起始坐标
     * @param radius 指定圆形半径
     * @return 返回一个flag标识以便确认是否处理成功
     */
    public static int imageCut_Circle(int xCoordinate, int yCoordinate, int radius) {
        int flag = 0;
        try{
            File input = new File(globalConfig.adbScreenshotName);
            File output = new File(globalConfig.adbScreenshot_path + "/screenshot_cut.png");
            BufferedImage image = ImageIO.read(input);
            BufferedImage result = imageCutByCircle(image, xCoordinate, yCoordinate, radius);
            ImageIO.write(result, "png", output);
        }catch (Exception error){
            error.printStackTrace();
        }
        return flag;
    }


    /**
     * 用于图片相似性比较，调用了下面两个方法
     * @param img1 图片1路径
     * @param img2 图片2路径
     * @throws Exception 暂时不支持抛出报错
     * @return 返回图片相似度，float强转int，有一定损失
     */
    public static int ImageCompare(String img1, String img2) {
        float percent = compare(getData(img1),
                getData(img2));
        if(percent==0){
            return 0;
        }else{
            return (int)percent;
        }
    }
    /**
     * 图片比较附属方法，用于取得图片的直方数据
     * @param name 图片路径
     * @return 图片直方数据
     */
    public static int[] getData(String name) {
        try{
            BufferedImage img = ImageIO.read(new File(name));
            BufferedImage slt = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            slt.getGraphics().drawImage(img, 0, 0, 100, 100, null);
            // ImageIO.write(slt,"jpeg",new File("slt.jpg"));
            int[] data = new int[256];
            for (int x = 0; x < slt.getWidth(); x++) {
                for (int y = 0; y < slt.getHeight(); y++) {
                    int rgb = slt.getRGB(x, y);
                    Color myColor = new Color(rgb);
                    int r = myColor.getRed();
                    int g = myColor.getGreen();
                    int b = myColor.getBlue();
                    data[(r + g + b) / 3]++;
                }
            }
            // data 就是所谓图形学当中的直方图的概念
            return data;
        }catch(Exception exception){
            System.out.println("有文件没有找到,请检查文件是否存在或路径是否正确");
            return null;
        }
    }
    /**
     * 图片比较附属方法，用于比较直方数据的相似度
     * @param s 源图片数据
     * @param t 目标图片数据
     * @return 返回浮点型相似度
     */
    public static float compare(int[] s, int[] t) {
        try{
            float result = 0F;
            for (int i = 0; i < 256; i++) {
                int abs = Math.abs(s[i] - t[i]);
                int max = Math.max(s[i], t[i]);
                result += (1 - ((float) abs / (max == 0 ? 1 : max)));
            }
            return (result / 256) * 100;
        }catch(Exception exception){
            return 0;
        }
    }
}
