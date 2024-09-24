package com.arknights.priestess.core;

import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.util.*;

public class System_ConfigHandler {
    /**
     * 转义特殊字符
     * @param input 输入字符串
     * @return 转义后的字符串
     */
    private static String escapeSpecialChars(String input) {
        return input.replace("\\", "\\\\");
    }

    /**
     * 更新或添加配置文件项
     * @param fileName 配置文件路径
     * @param key 键
     * @param value 值
     * @throws Exception 异常
     */
    public static void setProperty(String fileName, String key, String value) throws Exception {
        TreeMap<String, String> configMap = new TreeMap<>();

        // 加载配置文件
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(fileName)) {
            props.load(input);
        }
        for (String keyName : props.stringPropertyNames()) {
            configMap.put(keyName, props.getProperty(keyName));
        }

        // 更新或添加键值对
        configMap.put(key, value);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : configMap.entrySet()) {
                String keyString = escapeSpecialChars(entry.getKey());
                String valueString = escapeSpecialChars(entry.getValue());
                writer.write(keyString + "=" + valueString);
                writer.newLine();
            }
        }
    }

    /**
     * 读取配置文件项
     * @param configFile 配置文件路径
     * @param propName 配置名称
     * @return 配置内容
     */
    public static String getProperty(String configFile, String propName) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(configFile)){
            Properties prop = new Properties();
            prop.load(fileInputStream);

            return prop.getProperty(propName);
        }
    }

    /**
     * 读取配置文件所有内容
     * @param configFile 配置文件路径
     * @return 配置内容
     * @throws IOException 异常
     */
    public static Map<String, String> getAllProperties(String configFile) throws IOException {
        TreeMap<String, String> configMap = new TreeMap<>();
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(configFile)) {
            props.load(input);
        }
        for (String keyName : props.stringPropertyNames()) {
            configMap.put(keyName, props.getProperty(keyName));
        }
        return configMap;
    }
}
