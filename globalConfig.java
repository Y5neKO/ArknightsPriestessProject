/*
 * 全局配置信息，按需改动
 */
public class globalConfig {
    /*
     * 设定adb路径，请填写你的模拟器所属的adb.exe路径，需要以斜线结尾
     * 附常见的模拟器adb路径（推荐使用Nox模拟器）：
     * 夜神Nox模拟器：{夜神安装路径}/bin/
     * 蓝叠模拟器：{待补充}
     * {待补充}
     * 注：也可自定义其他的adb.exe路径，但一般模拟器开启adb时会附属运行一个adb进程，使用自定义的adb.exe会和模
     * 拟器附属adb冲突，而kill进程会花费接近数秒的时间，不仅可能影响命令执行，也会大大降低运行效率
     */
    public static final String adb_path = "D:\\Nox\\bin/";
}
