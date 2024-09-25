package com.arknights.priestess.springbootapi;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.file.*;

public class FileMonitorHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Path path = Paths.get("log/console.log");

        new Thread(() -> {
            try {
                // 使用 WatchService 来监听文件变动
                WatchService watchService = path.getFileSystem().newWatchService();
                path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey watchKey = watchService.take();
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        if (event.context().toString().equals(path.getFileName().toString())) {
                            String content = new String(Files.readAllBytes(path));
                            session.sendMessage(new TextMessage(content));  // 推送文件内容到 WebSocket 客户端
                        }
                    }
                    watchKey.reset();
                }
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}
