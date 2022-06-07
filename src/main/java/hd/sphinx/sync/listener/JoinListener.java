package hd.sphinx.sync.listener;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.mysql.ManageData;
import hd.sphinx.sync.util.AdvancementManager;
import hd.sphinx.sync.util.BukkitSerialization;
import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!ManageData.isPlayerInDB(p)) {
            if (ConfigManager.getBoolean("settings.sending.generated")) {
                p.sendMessage(ConfigManager.getColoredString("messages.generated"));
            }
            ManageData.generatePlayer(p);
        }
        p.sendMessage(ConfigManager.getColoredString("messages.loading"));
        p.getEnderChest().clear();
        p.getInventory().clear();
        p.setLevel(0);

        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                ManageData.loadPlayer(p);
            }
        }, 40l);
    }
}
