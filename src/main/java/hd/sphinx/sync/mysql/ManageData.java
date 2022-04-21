package hd.sphinx.sync.mysql;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.util.BukkitSerialization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageData {

    public static void generatePlayer(Player player) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            PreparedStatement tryps = MySQL.getConnection().prepareStatement("SELECT p.last_joined FROM player as p WHERE p.player_uuid = ?");
            tryps.setString(1, String.valueOf(player.getUniqueId()));
            ResultSet rs = tryps.executeQuery();
            if (rs.next()) {
                return;
            }
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
            }
        }
    }

    public static String loadPlayer(Player player) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT p.inventory FROM playerdata as p WHERE p.player_uuid = ?");
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ResultSet rs = ps.executeQuery();
            String result = "null";
            if (rs.next()) {
                result = rs.getString("inventory");
            }
            return result;
        } catch (SQLException ex) {
            if (!MySQL.isConnected()) {
                MySQL.connectMySQL();
            } else {
                ex.printStackTrace();
                Main.main.getLogger().warning("Something went wrong with loading a Player!");
            }
        }
        return "null";
    }

    public static void savePlayer(Player player, String base64) {
        if (!MySQL.isConnected()) {
            MySQL.connectMySQL();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE playerdata AS p SET p.player_name = ?, p.inventory = ?, p.last_joined = ? WHERE p.player_uuid = ?");
            ps.setString(1, player.getName());
            ps.setString(2, base64);
            java.util.Date dNow = new Date( );
            SimpleDateFormat ft =
                    new SimpleDateFormat ("MM.dd.yyyy G 'at' HH:mm:ss z");
            ps.setString(3, ft.format(dNow));
            ps.setString(4, String.valueOf(player.getUniqueId()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            if (!MySQL.isConnected()) {
                MySQL.connectMySQL();
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.main, new Runnable() {
                    @Override
                    public void run() {
                        savePlayer(player, base64);
                    }
                }, 20);
            } else {
                ex.printStackTrace();
                Main.main.getLogger().warning("Something went wrong with saving a Player!");
            }
        }
    }

    /**public static void setInventory(Player player) {
        try {
            ItemStack[] itemStacks = BukkitSerialization.itemStackArrayFromBase64(ManageData.loadPlayer(player));
            int i = 0;
            while (i <= 40) {
                player.getInventory().setItem(i, itemStacks[i]);
                i++;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }**/
}
