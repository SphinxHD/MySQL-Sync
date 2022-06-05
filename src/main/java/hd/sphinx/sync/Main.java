package hd.sphinx.sync;

import hd.sphinx.sync.listener.*;
import hd.sphinx.sync.mysql.MySQL;
import hd.sphinx.sync.util.ConfigManager;
import hd.sphinx.sync.util.ConfigUpdater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static Main main;
    public static Logger logger;

    public void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new JoinListener(), (Plugin) this);
        pluginManager.registerEvents((Listener) new QuitListener(), (Plugin) this);
    }

    public void registerMySQL() {
        MySQL.connectMySQL();
        try {
            MySQL.registerMySQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        logger = this.getLogger();

        Metrics metrics = new Metrics(this, 15003);

        ConfigUpdater.checkForUpdate();
        saveDefaultConfig();
        ConfigManager.reload();


        registerListener();
        registerMySQL();
        Bukkit.getPluginCommand("sync").setExecutor((CommandExecutor) new MainCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MySQL.disconnectMySQL();

    }
}
