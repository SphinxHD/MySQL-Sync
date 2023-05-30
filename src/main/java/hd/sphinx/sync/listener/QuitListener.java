package hd.sphinx.sync.listener;

import hd.sphinx.sync.MainManageData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!MainManageData.loadedPlayerData.contains(player)) {
            MainManageData.savePlayer(player);
        } else {
            MainManageData.loadedPlayerData.remove(player);
        }
    }
}
