package hd.sphinx.sync.listener;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.mysql.ManageData;
import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ManageData.loadInventory.add(player);
        if (!ManageData.isPlayerInDB(player)) {
            if (ConfigManager.getBoolean("settings.sending.generated")) {
                player.sendMessage(ConfigManager.getColoredString("messages.generated"));
            }
            ManageData.generatePlayer(player);
        }
        player.sendMessage(ConfigManager.getColoredString("messages.loading"));
        player.getEnderChest().clear();
        player.getInventory().clear();
        player.setLevel(0);

        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                ManageData.loadPlayer(player);
            }
        }, 40l);
    }
}
