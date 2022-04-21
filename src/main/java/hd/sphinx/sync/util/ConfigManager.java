package hd.sphinx.sync.util;

import hd.sphinx.sync.Main;

public class ConfigManager {

    public static String getString(String path) {
        return Main.main.getConfig().getString(path);
    }

    public static String getColoredString(String path) {
        return getString(path).replaceAll("%prefix%", getString("messages.prefix")).replaceAll("&", "§").replaceAll("&n", "\n");
    }
}
