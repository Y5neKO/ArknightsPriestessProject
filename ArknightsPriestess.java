/**
 * AKP - ArknightsPriestess
 * Author: Y5neKO
 * Date: 2022/4/30
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ArknightsPriestess {
    /**
     * 描述：无
     * 主要技术：无
     * 额外备注：无
     */
    public static void logo(){
        System.out.println("               _          _       _     _       \n" +
                "    /\\        | |        (_)     | |   | |      \n" +
                "   /  \\   _ __| | ___ __  _  __ _| |__ | |_ ___ \n" +
                "  / /\\ \\ | '__| |/ / '_ \\| |/ _` | '_ \\| __/ __|\n" +
                " / ____ \\| |  |   <| | | | | (_| | | | | |_\\__ \\\n" +
                "/_/    \\_\\_|  |_|\\_\\_| |_|_|\\__, |_| |_|\\__|___/\n" +
                "                             __/ |              \n" +
                "                            |___/               ");
        System.out.println(" _____      _           _                \n" +
                "|  __ \\    (_)         | |               \n" +
                "| |__) | __ _  ___  ___| |_ ___  ___ ___ \n" +
                "|  ___/ '__| |/ _ \\/ __| __/ _ \\/ __/ __|\n" +
                "| |   | |  | |  __/\\__ \\ ||  __/\\__ \\__ \\\n" +
                "|_|   |_|  |_|\\___||___/\\__\\___||___/___/     v0.1 by Dr.Y5neKO :)");
    }


    /**
     * 描述：创建所需目录，防止因为上一级目录不存在导致文件创建失败
     */
    public static void mkNecessaryDir(){

        String[] dirsName = {"log","screenshot"};       //定义
        int dirs = dirsName.length;

        for(int i = 0; i < dirs; i++){
            File dir = new File(System.getProperty("user.dir") + "\\" + dirsName[i]);
            dir.mkdir();
        }
    }

    /**
     * 描述：用于显示命令行帮助信息
     * 主要技术：无
     * 额外备注：无
     */
    public static void help(){
        System.out.println("AKP使用方法：\n");
        System.out.println("-------------------------");
        System.out.println("-h                          查看帮助");
        System.out.println("--adb-check                 检查adb连接");
        System.out.println("--adb-display               adb检查分辨率(使用前请务必先检查分辨率，若异常可能导致严重后果)");
        System.out.println("--adb-screenshot            adb截图及推送测试，默认目录为./screenshot/");
        System.out.println("--battle-autoclick          简单测试功能，模拟连点器点击“开始作战”按钮重合处，可实现一直刷当前关卡");
    }


    /**
     * 描述：用于检测adb连接信息
     * 主要技术：adb命令，输出流读取
     * 额外备注：无
     * @return 返回一个flag用以确认是否连接正常
     */
    public static int adb_check() {                                     //如果成功则返回int 1，反之返回0
        int flag = 0;

        System.out.println("正在检测中");
        System.out.println("-------------------------");

        String command_excute_string = command_excute(globalConfig.adb_path + " devices");

        if (command_excute_string.contains("device") & command_excute_string.contains("devices")){
            int index_first = command_excute_string.indexOf("\n");
            int index_last = command_excute_string.indexOf("device",index_first);

            if (index_first > 0 & index_last >0){
                String device_name = command_excute_string.substring(index_first+1,index_last);
                System.out.println("检测成功，当前设备为：\n" + device_name);
                flag = 1;
            }else {
                System.out.println("当前好像并没有设备连接噢，请查看log信息");
                System.out.println("log:\n" + command_excute_string);
            }
        } else if (command_excute_string.contains("e_code")){
            System.out.println("命令执行出错，请查看错误日志");
            errorLog("log.txt",command_excute_string);
            System.out.println("log:\n" + command_excute_string);
        } else {
            System.out.println("检测失败，请查看log信息");
            errorLog("log.txt",command_excute_string);
            System.out.println("log:\n" + command_excute_string);
        }
        return flag;
    }


    /**
     * 描述：用于通过adb检测分辨率
     * 主要技术：adb命令，输出流读取
     * 额外备注：无
     * @return 返回一个flag用以确认分辨率是否成功
     */
//    public static void adb_display(){
//
//        System.out.println("正在检测中");
//        System.out.println("-------------------------");
//
//        String command = globalConfig.adb_path + " shell wm size";
//        Runtime runtime = Runtime.getRuntime();             //创建一个runtime交互
//        try{
//            BufferedReader bfStream = new BufferedReader(   //创建一个bfstream读取字节流
//                    new InputStreamReader(                  //通过InputStramReader将字符流转换为字符流
//                            runtime.exec(command).getInputStream(),
//                            "GB2312"                        //指定编码
//                    )
//            );
//            String adbCheckDisplay = null;                             //定义字符串用来接收回显，初始化为null
//            while ((adbCheckDisplay = bfStream.readLine()) != null){
//                if (adbCheckDisplay.contains("size")){
//                    System.out.println("检测成功,，当前分辨率为：\n" + adbCheckDisplay);
//                    if (adbCheckDisplay.contains("1280x720")){
//                        System.out.println("当前分辨率设置正确，可正常使用");
//                    }else {
//                        System.out.println("!!!请注意!!!\n因为是采用adb模拟点击，所以AKP目前仅适用于1280x720分辨率，当前分辨率并不适应AKP。\n如果仍然使用，可能会导致预设的模拟点击位置异常，请前往模拟器修改\n刀客塔，您也不想生啖所有的源石罢？（笑");
//                    }
//                }
//            }
//
//        }catch (Exception error){
//            System.out.println("执行出错：");
//            System.out.println(error.toString());
//        }
//    }

//    public static void adb_display(){
//        System.out.println("正在检测中");
//        System.out.println("-------------------------");
//
//        String command_excute_string = command_excute(globalConfig.adb_path + " shell wm size");
//
//        //排除可能导致错误的信息
//        if (!command_excute_string.contains("e_code") & command_excute_string.contains("size"))                              //检查返回的结果是否报错
//        {
//            if (command_excute_string.contains("out of date.")){
//                System.out.println("adb被其他进程占用，可能会超时导致无回显，若无回显，请再执行一次，反之则忽略");
//            }
//            try{
//                BufferedReader reader = new BufferedReader(                         //创建一个bufferedreader对象
//                        new StringReader(command_excute_string)
//                );
//                String line = null;
//                while ((line = reader.readLine()) != null){
//                    if (line.contains("size")) {
//                        if (line.contains("1280x720")) {
//                            System.out.println("检测成功,，当前分辨率为：\n" + line);
//                            System.out.println("当前分辨率设置正确，可正常使用");
//                        } else {
//                            System.out.println("检测成功,，当前分辨率为：\n" + line);
//                            System.out.println("!!!请注意!!!\n因为是采用adb模拟点击，所以AKP目前仅适用于1280x720分辨率，当前分辨率并不适应AKP。\n如果仍然使用，可能会导致预设的模拟点击位置异常，请前往模拟器修改\n刀客塔，您也不想生啖所有的源石罢？（笑");
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("e_code:" + "未知异常");
//            }
//        }
//        else {
//            System.out.println("未知异常，请检查回显：\n" + command_excute_string);
//        }
//    }

    public static int adb_display(){                                        //检测分辨率是否正常，是则返回int 1，反之返回0
        int flag = 0;
        System.out.println("正在检测中，请稍等");
        System.out.println("-------------------------");

        String command_excute_string = command_excute(globalConfig.adb_path + " shell wm size");

        if (!command_excute_string.contains("e_code")){
            System.out.println("log:\n" + command_excute_string);
            if (command_excute_string.contains("Physical size:")){
                System.out.println("检测成功");
                if (command_excute_string.contains("1280x720")){
                    System.out.println("当前分辨率设置正确，可正常使用");
                    flag = 1;
                }else {
                    System.out.println("!!!请注意!!!\n因为是采用adb模拟点击，所以AKP目前仅适用于1280x720分辨率，当前分辨率并不适应AKP。\n如果仍然使用，可能会导致预设的模拟点击位置异常，请前往模拟器修改\n刀客塔，您也不想生啖所有的源石罢？（笑");
                }
            }else if (command_excute_string.contains("无回显命令")){
                System.out.println("当前无设备连接，请检查adb连接");
            } else {
                errorLog("log.txt",command_excute_string);
                System.out.println("检测失败，请查看log信息");
            }
        }else {
            System.out.println("命令执行出错，请查看log信息");
            errorLog("log.txt",command_excute_string);
            System.out.println("log:\n" + command_excute_string);
        }
        return flag;
    }


    /**
     * 描述：通过简单模拟点击实现无限刷当前关卡
     * 主要技术：无
     * 额外备注：无
     */
    public static void battle_auto_click(){
        int count = 1;
        while (true){
            String command_excute_string = command_excute(globalConfig.adb_path + " shell input tap 1125 649");
            if (!command_excute_string.contains("e_code")){
                System.out.println("sucess:模拟点击成功，当前次数：" + count);
                count++;
                try
                {
                    Thread.currentThread().sleep(1000);                 //延迟1000毫秒点击,防止跳屏过快造成bug
                }
                catch(Exception e){
                    System.out.println("WARNNING:sleep()方法出错，可忽略");
                    errorLog("log.txt",command_excute_string);
                }
            }else {
                System.out.println("error:命令执行出错,请查看log信息");
                errorLog("log.txt",command_excute_string);
                System.out.println("log:\n" + command_excute_string);
            }
        }
    }


    /**
     * 描述：当前场景识别
     */
    public static void scene_identification(){

    }


    /**
     * * 描述：命令执行总模块，用来执行系统命令并返回回显
    * 主要技术：无
    * 额外备注：因为没有引入adb模块，所以暂时需要通过系统命令来执行；若执行无回显的命令，则返回值为null类型
    */
    public static String command_excute(String cmd){
        Runtime runtime = Runtime.getRuntime();

        try{

            BufferedReader bfStream = new BufferedReader(   //创建一个bfStream进程读取字节流
                    new InputStreamReader(                  //通过InputStramReader将字符流转换为字符流
                            runtime.exec(cmd).getInputStream(),
                            "GB2312"                        //指定编码
                    )
            );
            String commandEcho = null;
            String commandEcho_2 = null;
            while ((commandEcho_2 = bfStream.readLine()) != null){
                if (commandEcho == null){
                    commandEcho = commandEcho_2;
                }else {
                    commandEcho = commandEcho + "\n" + commandEcho_2;
                }
            }
            bfStream.close();                               //释放刚刚创建的bfStream进程，以便节约内存
            if (commandEcho == null){                       //若无回显，返回值为null，可能会导致报错，因此作为特殊情况处理
                commandEcho = "无回显命令";
            }

            return commandEcho;

        }catch (Exception error) {

            String errorMsg = error.toString();
            errorLog("log.txt","*" + errorMsg);
            return "e_code:" + errorMsg;

        }
    }


    /**
     * 用以写入错误日志
     * @param logName 日志文件名
     * @param log 日志信息
     */
    public static void errorLog(String logName, String log){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(calendar.getTime()) + " | ";

        try{
            File file = new File(globalConfig.log_path + logName);
            //不存在则创建文件
            if(!file.exists()){
                file.createNewFile();
            }
            //创建写文件进程fileWritter，在日志底部追加
            FileWriter fileWritter = new FileWriter(file,true);
            fileWritter.write(time + log + "\n");
            fileWritter.close();
        }catch (IOException error){
            error.printStackTrace();
        }
    }


    /**
     * 描述：adb截图推送到电脑
     */
    public static int adbScreenshot(){
        int flag = 0;


        String command_excute_string = command_excute(globalConfig.adb_path + " shell screencap -p /sdcard/screenshot.png");
        if (!command_excute_string.contains("e_code")){
            command_excute_string = command_excute(globalConfig.adb_path + " pull /sdcard/screenshot.png ./screenshot");
            if (command_excute_string.contains("1 file pulled")){
                flag = 1;           //截图成功return标识为1
            }
        } else {
            errorLog(globalConfig.log_path + "log.txt", command_excute_string);
        }

        return flag;
    }

    /**
     * 方法重载，用来完成需要裁剪截屏的操作
     * @param x 指定x坐标
     * @param y 指定y坐标
     * @return  返回flag标识
     */
    public static int adbScreenshot(int x, int y){
        int flag = 0;


        String command_excute_string = command_excute(globalConfig.adb_path + " shell screencap -p /sdcard/screenshot.png");
        if (!command_excute_string.contains("e_code")){
            command_excute_string = command_excute(globalConfig.adb_path + " pull /sdcard/screenshot.png ./screenshot");
            if (command_excute_string.contains("1 file pulled")){
                flag = 1;           //截图成功return标识为1
            }
        } else {
            errorLog(globalConfig.log_path + "log.txt", command_excute_string);
        }

        return flag;
    }

    /**
     * 截取需要的范围用于图像识别
     */
//    public static int imageCut(){
//        int flag = 0;
//        adbScreenshot();
//        ImageIdentification.imageCut_Rectangle(790, 171, 100, 100);
//        return flag;
//    }

    /**
     * 主要模块，用以接收命令行参数
     * @param args 命令行参数
     */
    public static void main(String[] args){             //args接收命令行参数

        mkNecessaryDir();

        System.out.println("-------------------------");

        logo();

        System.out.println("-------------------------");

        if (args.length == 0){                          //检查是否输入参数
            System.out.println("请输入参数！！！");
            help();
        }

        if (args.length%2 > 0 & args.length != 1){                         //多种功能同时使用时会冲突，因此暂时只支持单功能使用（后续优化）
            System.out.println("参数功能冲突，请重新设定！！！");
            return;
        }



        for (String argument: args){            //循环接收命令行参数，传递给argument

            if(argument.equals("-h")){          //检查-h参数是否存在
                if (args.length > 1){
                    System.out.println("-h后续参数无效");
                    break;
                }
                help();
            }

            else if (argument.equals("--adb-check")){
                adb_check();
                System.out.println("-------------------------");
            }

            else if (argument.equals("--adb-display")){
                adb_display();
                System.out.println("-------------------------");
            }


            else if (argument.equals("--adb-screenshot")){
                int flag = adbScreenshot();
                if (flag == 1){
                    System.out.println("截图推送成功，请查看截图缓存目录");
                } else {
                    System.out.println("截图推送异常，请查看错误日志");
                }
            }

            else if (argument.equals("--battle-autoclick")) {
                int flag = adb_check();
                if (flag==1) {
                    battle_auto_click();
                }else{
                    System.out.println("adb连接异常，请检查adb连接和分辨率");
                }
                System.out.println("-------------------------");
            }
        }


        System.out.println("-------------------------");
        System.out.println("像素点rgb值识别方案测试");
        System.out.println("-------------------------");
        adbScreenshot();
        String botton_setting = ImageIdentification.getImageRGB_hex(globalConfig.adbScreenshotName, 36, 33);
        System.out.println(botton_setting);
        String botton_notice = ImageIdentification.getImageRGB_hex(globalConfig.adbScreenshotName, 128, 50);
        System.out.println(botton_notice);
        String botton_mail = ImageIdentification.getImageRGB_hex(globalConfig.adbScreenshotName, 203, 49);
        System.out.println(botton_mail);
        String botton_lizhi = ImageIdentification.getImageRGB_hex(globalConfig.adbScreenshotName, 754, 252);
        System.out.println(botton_lizhi);
        String botton_friends = ImageIdentification.getImageRGB_hex(globalConfig.adbScreenshotName, 355, 575);
        System.out.println(botton_friends);
        if (botton_setting.equals("#FFFFFF") & botton_notice.equals("#FFFFFF") & botton_mail.equals("#FFFFFF") & botton_lizhi.equals("#353535") & botton_friends.equals("#424242")){
            System.out.println("当前在主界面");
        }else {
            System.out.println("当前不在主界面");
        }
        System.out.println("-------------------------");

        System.out.println("OCR识别方案测试");
        System.out.println("-------------------------");
        adbScreenshot();
        ImageIdentification.imageCut_Rectangle(906, 125, 134, 70);
        String result = ImageIdentification.identification(globalConfig.adbScreenshot_path + "/screenshot_cut.png");
        System.out.println(result);
        if (result.contains("终端")){
            System.out.println("当前在主页");
        }else {
            System.out.println("当前不在主页");
        }
        System.out.println("-------------------------");

        System.out.println("图片特征相似度识别方案测试");
        System.out.println("-------------------------");
        adbScreenshot();
        ImageIdentification.imageCut_Rectangle(906, 125, 134, 70);
        int percent = ImageIdentification.ImageCompare(globalConfig.adbScreenshot_path + "screenshot_cut.png", "H:\\Java_Project\\ArknightsPriestess\\src\\img_feature\\index_terminal-906-125-134-70.png");
        System.out.println(percent);
        if(percent >= 85){
            System.out.println("当前在主页");
        }else {
            System.out.println("当前不在主页");
        }
        System.out.println("-------------------------");


        globalConfig.configRead();
    }
}
