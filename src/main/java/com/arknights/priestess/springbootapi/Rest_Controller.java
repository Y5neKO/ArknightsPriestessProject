package com.arknights.priestess.springbootapi;

import com.arknights.priestess.Console;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableScheduling
@RestController
public class Rest_Controller {
    // 加载 OpenCV 动态链接库
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    // 心跳包变量
    private static LocalDateTime lastRequestTime = LocalDateTime.now();

    // ============================API清单============================

    /**
     * 心跳包接口
     */
    @RestController
    public static class HeartbeatController {
        @GetMapping("/heartbeat")
        public String heartbeat() {
            lastRequestTime = LocalDateTime.now();
            return "Heartbeat received!";
        }
    }

    /**
     * 定时检查心跳包
     */
    @Scheduled(fixedRate = 10000)
    public void checkHeartbeat() {
        if (LocalDateTime.now().isAfter(lastRequestTime.plusSeconds(10))) {
            System.exit(0);
        }
    }

    /**
     * 测试接口
     * @return 测试接口的返回值
     * @throws Exception 如果抛出异常，则返回异常信息
     */
    @GetMapping("/api/main_console")
    public String main_console() throws Exception {
        Console console = new Console();
        console.main_console();
        return "Main console test.";
    }

    /**
     * 获取日志文件内容
     * @return 日志文件内容
     */
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

    /**
     * 清空日志文件
     * @return 清空日志文件的结果
     */
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

    /**
     * 获取屏幕截图
     * @return 响应实体
     * @throws IOException 如果读取文件失败，抛出异常
     */
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

    /**
     * 获取毛刺效果图像
     * @return 处理后的图像字节数组和响应头
     */
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
    // ============================附属处理函数============================
    /**
     * 添加文本水印到图像中
     * @param image 原图片
     * @param text 文本
     * @param fontPath 字体路径
     * @param fontSize 字体大小
     */
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

    /**
     * 应用毛刺效果
     * @param image 原始图像
     * @return 处理后的图像
     */
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
    /**
     * 将 Mat 转换为 BufferedImage
     * @param mat Mat 对象
     * @return BufferedImage 对象
     */
    private BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();
        byte[] data = new byte[width * height * channels];
        mat.get(0, 0, data);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = data[y * width * channels + x * channels + 2] & 0xFF; // R
                int g = data[y * width * channels + x * channels + 1] & 0xFF; // G
                int b = data[y * width * channels + x * channels] & 0xFF;     // B
                int rgb = (r << 16) | (g << 8) | b; // 组合 RGB
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }
}
