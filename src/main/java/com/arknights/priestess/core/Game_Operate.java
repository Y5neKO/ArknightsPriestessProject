package com.arknights.priestess.core;

import java.io.IOException;

/**
 * 游戏操作类
 */
public class Game_Operate {
    /**
     * 返回主界面
     * @return 是否成功
     * @throws IOException IO异常
     */
    public static boolean backMain() throws IOException, InterruptedException {
        String[] commands = {
                ADB_Main.click(264, 44),
                ADB_Main.click(97, 240)
        };

        if (Game_SceneIden.sceneIden().equals("ui_infrastructure")) {
            commands = new String[]{
                    ADB_Main.click(264, 44),
                    ADB_Main.click(97, 240),
                    ADB_Main.click(847, 493),
            };
        }

        System_Command system_command = new System_Command();
        system_command.runCommand(commands, 1000);

        while (!Game_SceneIden.sceneIden().equals("ui_main")){
            System.out.println(System_Log.buffer_logging("WARN", "返回失败，重试"));
            backMain();
        }

        return true;
    }
}
