package com.arknights.priestess.core;

import com.arknights.priestess.tools.Tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class System_Log {
    /**
     * 控制台日志
     * @param type 日志类型
     * @param msg 日志内容
     * @return 日志
     */
    public static String buffer_logging(String type, String msg) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDateTime_raw = now.format(formatter);
        String formattedDateTime = String.format("[%s] ", Tools.color(formattedDateTime_raw, "CYAN"));

        switch (type) {
            case "INFO":
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("INFO", "blue") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("INFO", "BLUE") + "] " + msg;
            case "EROR":
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("EROR", "red") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("EROR", "RED") + "] " + msg;
            case "WARN":
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("WARN", "yellow") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("WARN", "YELLOW") + "] " + msg;
            case "FAIL":
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("FAIL", "red") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("FAIL", "RED") + "] " + msg;
            case "SUCC":
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("SUCC", "green") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("SUCC", "GREEN") + "] " + msg;
            case "NULL":
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("NULL", "red") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("NULL", "RED") + "] " + msg;
            default:
                Tools.fileAppend("log/console.log", "<p>[" + Tools.color_html(formattedDateTime_raw, "cyan") + "] " + "[" + Tools.color_html("INFO", "white") + "] " + msg + "</p>");
                return formattedDateTime + "[" + Tools.color("INFO", "WHITE") + "] " + msg;
        }
    }
}
