package hd.sphinx.sync.util;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.mysql.MySQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Updater {

    public static Versions pluginVersion = Versions.ONETWO;
    public static Versions configVersion = Versions.ONEZERO;

    public static void checkForUpdate() {
        File file = new File("plugins/MySQL-Sync/config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (cfg.getString("version") != null) {
            if (cfg.getString("version").startsWith("1.1")) {
                configVersion = Versions.ONEONE;
            } else if (cfg.getString("version").startsWith("1.2")) {
                configVersion = Versions.ONETWO;
            } else {
                configVersion = Versions.ONETHREE;
            }
            if (pluginVersion == configVersion) return;
        }
        if (configVersion.equals(Versions.ONEZERO)) {
            updateONEZEROtoONETHREE();
        } else if (configVersion.equals(Versions.ONEONE)) {
            updateONEONEtoONETHREE();
        } else if (configVersion.equals(Versions.ONETWO)) {
            updateONETWOtoONETHREE();
        }
    }

    public static void checkForMySQLUpdate() {
        if (configVersion.equals(Versions.ONETHREE)) return;
        updateMySQLSyntaxToONETHREE();
    }

    public static void updateONEZEROtoONETHREE() {
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

    public static void updateONEONEtoONETHREE() {
        File file = new File("plugins/MySQL-Sync/config.yml");
        FileConfiguration oldCfg = YamlConfiguration.loadConfiguration(file);
        Boolean settings_syncing_inventory = oldCfg.getBoolean("settings.syncing.inventory");
        Boolean settings_syncing_enderchest = oldCfg.getBoolean("settings.syncing.enderchest");
        Boolean settings_syncing_exp = oldCfg.getBoolean("settings.syncing.exp");
        Boolean settings_syncing_gamemode = oldCfg.getBoolean("settings.syncing.gamemode");
        Boolean settings_syncing_hunger = oldCfg.getBoolean("settings.syncing.hunger");
        Boolean settings_syncing_health = oldCfg.getBoolean("settings.syncing.health");

        Boolean settings_sending_generated = oldCfg.getBoolean("settings.sending.generated");
        Boolean settings_sending_error = oldCfg.getBoolean("setting.sending.error");

        Boolean settings_use_permission = oldCfg.getBoolean("settings.use-permission");

        String messages_prefix = oldCfg.getString("messages.prefix");
        String messages_generated = oldCfg.getString("messages.generated");
        String messages_loading = oldCfg.getString("messages.loading");
        String messages_loaded = oldCfg.getString("messages.loaded");
        String messages_error = oldCfg.getString("messages.error");
        String messages_help = oldCfg.getString("messages.help");
        String messages_version = oldCfg.getString("messages.version");
        String messages_reload = oldCfg.getString("messages.reload");
        String messages_lacking_permission = oldCfg.getString("messages.lacking-permission");

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

        FileConfiguration cfg = Main.main.getConfig();
        cfg.set("settings.syncing.inventory", settings_syncing_inventory);
        cfg.set("settings.syncing.enderchest", settings_syncing_enderchest);
        cfg.set("settings.syncing.exp", settings_syncing_exp);
        cfg.set("settings.syncing.gamemode", settings_syncing_gamemode);
        cfg.set("settings.syncing.hunger", settings_syncing_hunger);
        cfg.set("settings.syncing.health", settings_syncing_health);

        cfg.set("settings.sending.generated", settings_sending_generated);
        cfg.set("settings.sending.error", settings_sending_error);

        cfg.set("settings.use-permission", settings_use_permission);

        cfg.set("messages.prefix", messages_prefix);
        cfg.set("messages.generated", messages_generated);
        cfg.set("messages.loading", messages_loading);
        cfg.set("messages.loaded", messages_loaded);
        cfg.set("messages.error", messages_error);
        cfg.set("messages.help", messages_help);
        cfg.set("messages.version", messages_version);
        cfg.set("messages.reload", messages_reload);
        cfg.set("messages.lacking-perission", messages_lacking_permission);

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

    public static void updateONETWOtoONETHREE() {
        File file = new File("plugins/MySQL-Sync/config.yml");
        FileConfiguration oldCfg = YamlConfiguration.loadConfiguration(file);
        Boolean settings_syncing_inventory = oldCfg.getBoolean("settings.syncing.inventory");
        Boolean settings_syncing_enderchest = oldCfg.getBoolean("settings.syncing.enderchest");
        Boolean settings_syncing_exp = oldCfg.getBoolean("settings.syncing.exp");
        Boolean settings_syncing_gamemode = oldCfg.getBoolean("settings.syncing.gamemode");
        Boolean settings_syncing_hunger = oldCfg.getBoolean("settings.syncing.hunger");
        Boolean settings_syncing_health = oldCfg.getBoolean("settings.syncing.health");
        Boolean settings_syncing_effects = oldCfg.getBoolean("settings.syncing.effects");
        Boolean settings_syncing_advancements = oldCfg.getBoolean("settings.syncing.advancements");

        Boolean settings_sending_generated = oldCfg.getBoolean("settings.sending.generated");
        Boolean settings_sending_error = oldCfg.getBoolean("setting.sending.error");

        Boolean settings_use_permission = oldCfg.getBoolean("settings.use-permission");

        String messages_prefix = oldCfg.getString("messages.prefix");
        String messages_generated = oldCfg.getString("messages.generated");
        String messages_loading = oldCfg.getString("messages.loading");
        String messages_loaded = oldCfg.getString("messages.loaded");
        String messages_error = oldCfg.getString("messages.error");
        String messages_help = oldCfg.getString("messages.help");
        String messages_version = oldCfg.getString("messages.version");
        String messages_reload = oldCfg.getString("messages.reload");
        String messages_lacking_permission = oldCfg.getString("messages.lacking-permission");

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

        FileConfiguration cfg = Main.main.getConfig();
        cfg.set("settings.syncing.inventory", settings_syncing_inventory);
        cfg.set("settings.syncing.enderchest", settings_syncing_enderchest);
        cfg.set("settings.syncing.exp", settings_syncing_exp);
        cfg.set("settings.syncing.gamemode", settings_syncing_gamemode);
        cfg.set("settings.syncing.hunger", settings_syncing_hunger);
        cfg.set("settings.syncing.health", settings_syncing_health);
        cfg.set("settings.syncing.effects", settings_syncing_effects);
        cfg.set("settings.syncing.advancements", settings_syncing_advancements);

        cfg.set("settings.sending.generated", settings_sending_generated);
        cfg.set("settings.sending.error", settings_sending_error);

        cfg.set("settings.use-permission", settings_use_permission);

        cfg.set("messages.prefix", messages_prefix);
        cfg.set("messages.generated", messages_generated);
        cfg.set("messages.loading", messages_loading);
        cfg.set("messages.loaded", messages_loaded);
        cfg.set("messages.error", messages_error);
        cfg.set("messages.help", messages_help);
        cfg.set("messages.version", messages_version);
        cfg.set("messages.reload", messages_reload);
        cfg.set("messages.lacking-perission", messages_lacking_permission);

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

    public static void updateMySQLSyntaxToONETHREE() {
        try {
            MySQL.getConnection().prepareStatement("ALTER TABLE sync.playerdata ADD effects LONGTEXT NULL;").executeUpdate();
        } catch (SQLException ignored) { }
        try {
            MySQL.getConnection().prepareStatement("ALTER TABLE sync.playerdata ADD advancements LONGTEXT NULL;").executeUpdate();
        } catch (SQLException ignored) { }
        try {
            MySQL.getConnection().prepareStatement("ALTER TABLE sync.playerdata ADD statistics LONGTEXT NULL;").executeUpdate();
        } catch (SQLException ignored) { }
    }

    public static enum Versions {

        ONEZERO(true),
        ONEONE(true),
        ONETWO(true),
        ONETHREE(false);

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
