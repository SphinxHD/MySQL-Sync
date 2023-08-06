package hd.sphinx.sync;

import hd.sphinx.sync.listener.*;
import hd.sphinx.sync.util.ConfigManager;
import hd.sphinx.sync.util.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static Main main;
    public static Logger logger;

    public static Boolean isStopping = false;

    public void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents((Listener) new JoinListener(), (Plugin) this);
        pluginManager.registerEvents((Listener) new QuitListener(), (Plugin) this);
        pluginManager.registerEvents((Listener) new DeathListener(), (Plugin) this);
        pluginManager.registerEvents((Listener) new CommandListener(), (Plugin) this);
    }

    public static Boolean isUpdateAvailable() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/SphinxHD/MySQL-Sync/main/newest-version");
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String version = bufferedReader.readLine();
            bufferedReader.close();
            return !(version.equalsIgnoreCase(ConfigManager.getString("version")));
        } catch (IOException ignored) {
            return false;
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        logger = this.getLogger();

        Metrics metrics = new Metrics(this, 15003);

        saveDefaultConfig();
        ConfigManager.reload();

        registerListener();
        MainManageData.initialize();
        Updater.checkForMySQLUpdate();
        Bukkit.getPluginCommand("sync").setExecutor((CommandExecutor) new MainCommand());
        Bukkit.getPluginCommand("sync").setTabCompleter(new MainCommandTabComplete());

        Updater.checkForUpdate();
        if (isUpdateAvailable()) {
            logger.log(Level.WARNING, "MySQL Sync is not up to date. Please download the newest version on Spigot: https://www.spigotmc.org/resources/mysql-sync.101554/");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (isStopping) return;
        isStopping = true;
        MainManageData.startShutdown();
    }
}
