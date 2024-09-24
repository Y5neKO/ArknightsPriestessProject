package com.arknights.priestess.copyright;


import com.arknights.priestess.tools.Tools;

public class Copyright {
    public static String logo = "\n" +
            "██████╗ ██████╗ ██╗███████╗███████╗████████╗███████╗███████╗███████╗\n" +
            "██╔══██╗██╔══██╗██║██╔════╝██╔════╝╚══██╔══╝██╔════╝██╔════╝██╔════╝\n" +
            "██████╔╝██████╔╝██║█████╗  ███████╗   ██║   █████╗  ███████╗███████╗\n" +
            "██╔═══╝ ██╔══██╗██║██╔══╝  ╚════██║   ██║   ██╔══╝  ╚════██║╚════██║\n" +
            "██║     ██║  ██║██║███████╗███████║   ██║   ███████╗███████║███████║\n" +
            "╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝╚══════╝╚══════╝\n" +
            "                                                        v%s by %s :)\n" +
            "                                                        GitHub: https://github.com/Y5neKO\n";

    public static String getLogo() {
        return String.format(logo, Tools.color("0.2", "CYAN"), Tools.color("Y5neKO", "YELLOW"));
    }
}
