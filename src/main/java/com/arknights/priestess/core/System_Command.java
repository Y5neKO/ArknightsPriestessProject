package com.arknights.priestess.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class System_Command {
    // 获取Runtime对象
    Runtime runtime = Runtime.getRuntime();

    Map<Integer, String> commandResultMap = new HashMap<>();

    int commandIndex = 1;

    /**
     * 执行命令集
     * @param commands 命令集
     * @return 是否执行成功
     */
    public boolean runCommand(String[] commands, int timeout) {

        for (String command : commands) {
            try {
                Process process = runtime.exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder resultSB = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    resultSB.append(line).append("\n");
                }
                String result = resultSB.toString();
                reader.close();
                if (result.endsWith("\n")) {
                    result = result.substring(0, result.length() - 1);
                }
                commandResultMap.put(commandIndex, result);
                commandIndex++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }

            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
    /**
     * 0延迟重载
     * @param commands 命令集
     * @return 是否执行成功
     */
    public boolean runCommand(String[] commands){
        return runCommand(commands,0);
    }

    /**
     * 获取命令执行结果
     * @param commandIndex 命令序号
     * @return 命令执行结果
     */
    public String getCommandResult(int commandIndex) {
        if (commandIndex == 0){
            return commandResultMap.get(commandResultMap.size());
        } else {
            return commandResultMap.get(commandIndex);
        }
    }
}
