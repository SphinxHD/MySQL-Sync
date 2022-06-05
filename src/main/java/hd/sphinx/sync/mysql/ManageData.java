package hd.sphinx.sync.mysql;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.util.ConfigManager;
import hd.sphinx.sync.util.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import sun.security.krb5.Config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageData {

    public static Boolean isPlayerInDB(Player player) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            PreparedStatement tryps = MySQL.getConnection().prepareStatement("SELECT p.last_joined FROM playerdata as p WHERE p.player_uuid = ?");
            tryps.setString(1, String.valueOf(player.getUniqueId()));
            ResultSet rs = tryps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            return false;
        }
    }

    public static void generatePlayer(Player player) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO playerdata (player_uuid, player_name, last_joined) VALUES(?,?,?)");
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ps.setString(2, player.getName());
            java.util.Date dNow = new Date( );
            SimpleDateFormat ft =
                    new SimpleDateFormat ("MM.dd.yyyy G 'at' HH:mm:ss z");
            ps.setString(3, ft.format(dNow));
            ps.executeUpdate();
        } catch (SQLException ex) {
            if (!MySQL.isConnected()) {
                MySQL.connectMySQL();
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
                    @Override
                    public void run() {
                        generatePlayer(player);
                    }
                }, 20);
            } else {
                ex.printStackTrace();
                Main.main.getLogger().warning("Something went wrong with registering a Player!");
                if (ConfigManager.getBoolean("settings.sending.error")) {
                    player.sendMessage(ConfigManager.getColoredString("messages.error"));
                }
            }
        }
    }

    public static void loadPlayer(Player player) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM playerdata as p WHERE p.player_uuid = ?");
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ResultSet rs = ps.executeQuery();
            String result = "null";
            if (rs.next()) {
                result = rs.getString("inventory");
                InventoryManager.loadItem(result, player);
                result = rs.getString("gamemode");
                if (result != null) {
                    player.setGameMode(GameMode.valueOf(result));
                }
                result = rs.getString("health");
                if (result != null) {
                    player.setHealth(Double.parseDouble(result));
                }
                result = rs.getString("food");
                if (result != null) {
                    player.setFoodLevel(Integer.parseInt(result));
                }
                result = rs.getString("exp");
                if (result != null) {
                    player.setLevel(Integer.parseInt(result));
                }
                result = rs.getString("enderchest");
                InventoryManager.loadEChest(result, player);
                player.sendMessage(ConfigManager.getColoredString("messages.loaded"));
            }
        } catch (SQLException ex) {
            if (!MySQL.isConnected()) {
                MySQL.connectMySQL();
            } else {
                ex.printStackTrace();
                Main.main.getLogger().warning("Something went wrong with loading a Player!");
                if (ConfigManager.getBoolean("settings.sending.error")) {
                    player.sendMessage(ConfigManager.getColoredString("messages.error"));
                }
            }
        }
    }

    public static void savePlayer(Player player, String invBase64, String ecBase64) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            String statement = "UPDATE playerdata AS p SET p.player_name = ?, p.last_joined = ?";
            if (ConfigManager.getBoolean("settings.syncing.inventory")) {
                statement = statement + ", p.inventory = ?";
            } else if (ConfigManager.getBoolean("settings.syncing.gamemode")) {
                statement = statement + ", p.gamemode = ?";
            } else if (ConfigManager.getBoolean("settings.syncing.health")) {
                statement = statement + ", p.health = ?";
            } else if (ConfigManager.getBoolean("settings.syncing.hunger")) {
                statement = statement + ", p.food = ?";
            } else if (ConfigManager.getBoolean("settings.syncing.enderchest")) {
                statement = statement + ", p.enderchest = ?";
            } else if (ConfigManager.getBoolean("settings.syncing.exp")) {
                statement = statement + ", p.exp = ?";
            }
            statement = statement + " WHERE p.player_uuid = ?";
            PreparedStatement ps = MySQL.getConnection().prepareStatement(statement);
            ps.setString(1, player.getName());
            Date dNow = new Date( );
            SimpleDateFormat ft =
                    new SimpleDateFormat ("MM.dd.yyyy G 'at' HH:mm:ss z");
            ps.setString(2, ft.format(dNow));

            String[] args = statement.split(",");

            int real = 1;
            for (String str : args) {
                if (str.contains("inventory")) {
                    ps.setString(real, invBase64);
                } else if (str.contains("gamemode")) {
                    ps.setString(real, String.valueOf(player.getGameMode()));
                } else if (str.contains("health")) {
                    ps.setInt(real, (int) player.getHealth());
                } else if (str.contains("food")) {
                    ps.setInt(real, player.getFoodLevel());
                } else if (str.contains("enderchest")) {
                    ps.setString(real, ecBase64);
                } else if (str.contains("exp")) {
                    ps.setInt(real, player.getLevel());
                }
                real++;
            }
            ps.setString(real, String.valueOf(player.getUniqueId()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            if (!MySQL.isConnected()) {
                MySQL.connectMySQL();
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
                    @Override
                    public void run() {
                        savePlayer(player, invBase64, ecBase64);
                    }
                }, 20);
            } else {
                ex.printStackTrace();
                Main.main.getLogger().warning("Something went wrong with saving a Player!");
                if (ConfigManager.getBoolean("settings.sending.error")) {
                    player.sendMessage(ConfigManager.getColoredString("messages.error"));
                }
            }
        }
    }
}
