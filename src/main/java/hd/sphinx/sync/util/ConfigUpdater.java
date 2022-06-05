package hd.sphinx.sync.util;

import hd.sphinx.sync.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUpdater {

    public static Versions pluginVersion = Versions.ONEONE;
    public static Versions configVersion = Versions.ONEZERO;

    public static void checkForUpdate() {
        File file = new File("plugins/MySQL-Sync/config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (cfg.getString("version") != null) {
            configVersion = Versions.ONEONE;
            if (pluginVersion == configVersion) return;
        }
        updateONEZEROtoONEONE();
    }

    public static void updateONEZEROtoONEONE() {
        File file = new File("plugins/MySQL-Sync/config.yml");
        FileConfiguration oldCfg = YamlConfiguration.loadConfiguration(file);
        String messages_prefix = oldCfg.getString("messages.prefix");
        String messages_generated = oldCfg.getString("messages.generated");
        String messages_loading = oldCfg.getString("messages.loading");
        String messages_loads = oldCfg.getString("messages.loads");
        String messages_error = oldCfg.getString("messages.error");

        String mysql_host = oldCfg.getString("mysql.host");
        String mysql_port = oldCfg.getString("mysql.port");
        String mysql_database = oldCfg.getString("mysql.database");
        String mysql_username = oldCfg.getString("mysql.username");
        String mysql_password = oldCfg.getString("mysql.password");

        if (!file.delete()) {
            System.out.println("ERROR!");
            return;
        }
        Main.main.saveDefaultConfig();

        File newFile = new File("plugins/MySQL-Sync/config.yml");
        FileConfiguration cfg = Main.main.getConfig();
        cfg.set("messages.prefix", messages_prefix);
        cfg.set("messages.generated", messages_generated);
        cfg.set("messages.loading", messages_loading);
        cfg.set("messages.loaded", messages_loads);
        cfg.set("messages.error", messages_error);

        cfg.set("mysql.host", mysql_host);
        cfg.set("mysql.port", mysql_port);
        cfg.set("mysql.database", mysql_database);
        cfg.set("mysql.username", mysql_username);
        cfg.set("mysql.password", mysql_password);

        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static enum Versions {

        ONEZERO(true),
        ONEONE(false);

        private Boolean update;

        Versions(Boolean update) {
            this.update = update;
        }

        public void setUpdate(Boolean newValue) {
            update = newValue;
        }

        public Boolean getUpdate() {
            return update;
        }
    }
}
