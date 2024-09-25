package com.arknights.priestess.springbootapi;

import com.arknights.priestess.Console;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@RestController
public class Rest_Controller {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @GetMapping("/api/main_console")
    public String main_console() throws Exception {
        Console console = new Console();
        console.main_console();
        return "Hello from Spring Boot!";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/api/console_log")
    public String console_log() {
        String filePath = "log/console.log";
        StringBuilder content = new StringBuilder();

        try {
            // 读取文件内容为 List<String>
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            // 打印文件内容
            for (String line : lines) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "false";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/api/clear_log")
    public String clear_log() {
        String filePath = "log/console.log";
        try {
            Files.newBufferedWriter(Paths.get(filePath)).close(); // 不写入任何内容，清空文件
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return "Cleared log file successfully";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/api/screenshot")
    public ResponseEntity<byte[]> getImage() throws IOException {
        // 拼接图片的完整路径
        Path imagePath = Paths.get("screenshots/screenshot.png");
        // 检查文件是否存在
        if (!Files.exists(imagePath)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 如果文件不存在，返回404
        }
        // 读取图片为字节数组
        byte[] imageBytes = Files.readAllBytes(imagePath);
        // 设置响应头为图片类型
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        // 返回图片字节数组和响应头
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

                                        //    @CrossOrigin(origins = "*", allowedHeaders = "*")
                                        //    @GetMapping("/api/eyes-of-priestess")
                                        //    public ResponseEntity<ByteArrayResource> getMonitorEffectImage() {
                                        //        // 读取原始图像
                                        //        Mat originalImage = Imgcodecs.imread("screenshots/screenshot.png");
                                        //
                                        //        // 检查图像是否加载成功
                                        //        if (originalImage.empty()) {
                                        //            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                                        //        }
                                        //
                                        //        // 创建监控器效果图像
                                        //        Mat monitorImage = addMonitorEffect(originalImage);
                                        //
                                        //        // 在图像底部添加水印
                                        //        addWatermark(monitorImage, "Watermark Text");
                                        //
                                        //        // 将 Mat 转换为字节数组
                                        //        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        //        try {
                                        //            BufferedImage bufferedImage = matToBufferedImage(monitorImage);
                                        //            ImageIO.write(bufferedImage, "png", baos);
                                        //            baos.flush();
                                        //        } catch (IOException e) {
                                        //            e.printStackTrace();
                                        //            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                                        //        }
                                        //
                                        //        // 返回图像字节流
                                        //        byte[] imageBytes = baos.toByteArray();
                                        //        ByteArrayResource resource = new ByteArrayResource(imageBytes);
                                        //
                                        //        return ResponseEntity.ok()
                                        //                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=monitor_effect_image.png")
                                        //                .contentLength(imageBytes.length)
                                        //                .body(resource);
                                        //    }
                                        //
                                        //    private Mat addMonitorEffect(Mat image) {
                                        //        int width = image.cols();
                                        //        int height = image.rows();
                                        //        // 创建新的图像以添加监控器效果，增加边框
                                        //        Mat monitorImage = new Mat(height + 20, width + 20, image.type());
                                        //        // 将原始图像放置在新图像中
                                        //        image.copyTo(monitorImage.submat(10, height + 10, 10, width + 10));
                                        //        // 添加模糊效果
                                        //        Imgproc.blur(monitorImage, monitorImage, new Size(5, 5));
                                        //        // 添加噪声
                                        //        addNoise(monitorImage);
                                        //
                                        //        return monitorImage;
                                        //    }
                                        //
                                        //    private void addNoise(Mat image) {
                                        //        // 添加噪声
                                        //        for (int i = 0; i < 10000; i++) {
                                        //            int x = (int) (Math.random() * image.cols());
                                        //            int y = (int) (Math.random() * image.rows());
                                        //            // 将噪声像素随机设置为 RGB
                                        //            image.put(y, x, new byte[]{(byte) (Math.random() * 255), (byte) (Math.random() * 255), (byte) (Math.random() * 255)});
                                        //        }
                                        //    }
                                        //
                                        //    private void addWatermark(Mat image, String text) {
                                        //        int width = image.cols();
                                        //        int height = image.rows();
                                        //
                                        //        // 设置字体、大小和颜色
                                        //        int fontFace = Imgproc.FONT_HERSHEY_SIMPLEX;
                                        //        double fontScale = 1.0;
                                        //        int thickness = 2;
                                        //        Scalar color = new Scalar(255, 255, 255); // 白色
                                        //
                                        //        // 计算文本大小
                                        //        Size textSize = Imgproc.getTextSize(text, fontFace, fontScale, thickness, null);
                                        //        int textWidth = (int) textSize.width;
                                        //        int textHeight = (int) textSize.height;
                                        //
                                        //        // 计算水印位置 (居中)
                                        //        int x = (width - textWidth) / 2;
                                        //        int y = height - 10; // 水印离底部10个像素
                                        //
                                        //        // 添加水印
                                        //        Imgproc.putText(image, text, new Point(x, y), fontFace, fontScale, color, thickness);
                                        //    }
                                        //
                                        //    private BufferedImage matToBufferedImage(Mat mat) {
                                        //        int width = mat.cols();
                                        //        int height = mat.rows();
                                        //        int channels = mat.channels();
                                        //        byte[] data = new byte[width * height * channels];
                                        //        mat.get(0, 0, data);
                                        //
                                        //        // 创建一个新的 BufferedImage，并使用 RGB 颜色模型
                                        //        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                                        //
                                        //        // 将数据从 Mat 设置到 BufferedImage
                                        //        for (int y = 0; y < height; y++) {
                                        //            for (int x = 0; x < width; x++) {
                                        //                int r = data[y * width * channels + x * channels + 2] & 0xFF; // R
                                        //                int g = data[y * width * channels + x * channels + 1] & 0xFF; // G
                                        //                int b = data[y * width * channels + x * channels + 0] & 0xFF; // B
                                        //                int rgb = (r << 16) | (g << 8) | b; // 组合 RGB
                                        //                image.setRGB(x, y, rgb);
                                        //            }
                                        //        }
                                        //
                                        //        return image;
                                        //    }

//    @GetMapping("/api/glitch-effect")
//    public ResponseEntity<ByteArrayResource> getGlitchEffectImage() {
//        Mat image = Imgcodecs.imread("screenshots/screenshot.png");
//
//        if (image.empty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        Mat glitchedImage = applyGlitchEffect(image);
//
//        // 将处理后的图像写入字节流
//        MatOfByte matOfByte = new MatOfByte();
//        Imgcodecs.imencode(".jpg", glitchedImage, matOfByte); // 将处理后的图像编码到 MatOfByte
//
//        byte[] imageBytes = matOfByte.toArray(); // 从 MatOfByte 获取字节数组
//        ByteArrayResource resource = new ByteArrayResource(imageBytes);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=glitched_image.jpg")
//                .contentLength(imageBytes.length)
//                .body(resource);
//    }
//
//    private Mat applyGlitchEffect(Mat image) {
//        Mat noiseImage = new Mat(image.size(), image.type());
//        Random random = new Random();
//
//        // 向图像中添加噪声
//        for (int i = 0; i < image.rows(); i++) {
//            for (int j = 0; j < image.cols(); j++) {
//                // 随机决定是否添加噪声
//                if (random.nextDouble() < 0.05) { // 5%的概率
//                    // 随机生成噪声值
//                    byte[] pixel = new byte[3];
//                    pixel[0] = (byte) random.nextInt(256); // B
//                    pixel[1] = (byte) random.nextInt(256); // G
//                    pixel[2] = (byte) random.nextInt(256); // R
//                    noiseImage.put(i, j, pixel);
//                } else {
//                    // 保持原始像素值
//                    noiseImage.put(i, j, image.get(i, j));
//                }
//            }
//        }
//
//        // 模糊处理
//        Imgproc.GaussianBlur(noiseImage, noiseImage, new Size(3, 3), 0);
//
//        // 调整对比度
//        Mat contrastedImage = new Mat();
//        Core.convertScaleAbs(noiseImage, contrastedImage, 1.2, 0); // 增加对比度
//
//        return contrastedImage;
//    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/api/eyes-of-priestess")
    public ResponseEntity<ByteArrayResource> getGlitchEffectImage() {
        Mat image = Imgcodecs.imread("screenshots/screenshot.png");

        if (image.empty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // 添加文本水印
        String watermarkText = "EYESOFPRIESTESS -RI03- CCTV//";
        String fontPath = "files/Press Start 2P.ttf"; // 自定义字体路径
        int fontSize = 25;
        addTextToImage(image, watermarkText, fontPath, fontSize);
        // 应用毛刺效果
        Mat glitchedImage = applyGlitchEffect(image);
        // 将处理后的图像写入字节流
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", glitchedImage, matOfByte); // 将处理后的图像编码到 MatOfByte
        byte[] imageBytes = matOfByte.toArray(); // 从 MatOfByte 获取字节数组
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=glitched_image.jpg")
                .contentLength(imageBytes.length)
                .body(resource);
    }
    private void addTextToImage(Mat image, String text, String fontPath, int fontSize) {
        BufferedImage bufferedImage = matToBufferedImage(image);
        Graphics2D g = bufferedImage.createGraphics();
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(Font.PLAIN, fontSize);
            g.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }
        // 设置边框颜色和字体颜色
        g.setColor(Color.BLACK); // 黑色边框
        g.setStroke(new BasicStroke(6)); // 边框厚度
        // 计算文本位置
        FontMetrics fm = g.getFontMetrics();
        int x = (bufferedImage.getWidth() - fm.stringWidth(text)) / 2;
        int y = bufferedImage.getHeight() - fm.getHeight();
        // 绘制边框
        g.drawString(text, x - 1, y); // 上
        g.drawString(text, x + 1, y); // 下
        g.drawString(text, x, y - 1); // 左
        g.drawString(text, x, y + 1); // 右
        // 绘制文本
        g.setColor(Color.WHITE); // 重新设置为白色
        g.drawString(text, x, y);
        g.dispose();
        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        image.put(0, 0, data);
    }
    private Mat applyGlitchEffect(Mat image) {
        Mat noiseImage = new Mat(image.size(), image.type());
        Random random = new Random();
        // 向图像中添加噪声
        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                // 随机决定是否添加噪声
                if (random.nextDouble() < 0.05) { // 5%的概率
                    // 随机生成噪声值
                    byte[] pixel = new byte[3];
                    pixel[0] = (byte) random.nextInt(256); // B
                    pixel[1] = (byte) random.nextInt(256); // G
                    pixel[2] = (byte) random.nextInt(256); // R
                    noiseImage.put(i, j, pixel);
                } else {
                    // 保持原始像素值
                    noiseImage.put(i, j, image.get(i, j));
                }
            }
        }
        // 模糊处理
        Imgproc.GaussianBlur(noiseImage, noiseImage, new Size(3, 3), 0);
        // 调整对比度
        Mat contrastedImage = new Mat();
        Core.convertScaleAbs(noiseImage, contrastedImage, 1.2, 0); // 增加对比度
        return contrastedImage;
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();
        byte[] data = new byte[width * height * channels];
        mat.get(0, 0, data);

        // 创建一个新的 BufferedImage，并使用 RGB 颜色模型
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        // 将数据从 Mat 设置到 BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = data[y * width * channels + x * channels + 2] & 0xFF; // R
                int g = data[y * width * channels + x * channels + 1] & 0xFF; // G
                int b = data[y * width * channels + x * channels + 0] & 0xFF; // B
                int rgb = (r << 16) | (g << 8) | b; // 组合 RGB
                image.setRGB(x, y, rgb);
            }
        }

        return image;
    }
}
