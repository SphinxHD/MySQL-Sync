package hd.sphinx.sync.util;

import hd.sphinx.sync.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    public static FileConfiguration config = null;
    public static FileConfiguration language = null;

    public static void reload() {
        File file = new File("plugins/MySQL-Sync/config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        File languageFile = new File("plugins/MySQL-Sync/lang/" + getString("settings.language") + ".yml");
        if (!languageFile.exists()) {
            Main.main.saveResource("lang/en_EN.yml", false);
            Main.main.saveResource("lang/de_DE.yml", false);
            Main.main.saveResource("lang/no_NO.yml", false);
            Main.main.saveResource("lang/ru_RU.yml", false);
        }
        File languageFileTwo = new File("plugins/MySQL-Sync/lang/no_NO.yml");
        if (!languageFileTwo.exists()) {
            Main.main.saveResource("lang/no_NO.yml", false);
            Main.main.saveResource("lang/ru_RU.yml", false);
        }
        language = YamlConfiguration.loadConfiguration(languageFile);
    }

    public static String getString(String path) {
        if (path.contains("messages")) return language.getString(path.replace("messages.", ""));
        return config.getString(path);
    }

    public static String getColoredString(String path) {
        return getString(path).replaceAll("%prefix%", getString("messages.prefix")).replaceAll("&", "§").replaceAll("&n", "\n");
    }

    public static Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }
}
