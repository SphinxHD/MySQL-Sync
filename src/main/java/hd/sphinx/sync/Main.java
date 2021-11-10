package hd.sphinx.sync;

import hd.sphinx.sync.listener.*;
import hd.sphinx.sync.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin {

    public static Main main;

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
        registerListener();
        registerMySQL();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MySQL.disconnectMySQL();

    }
}
