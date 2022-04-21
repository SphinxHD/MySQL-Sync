package hd.sphinx.sync.listener;

import hd.sphinx.sync.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            InventoryManager.generatePlayer(p);
            return;
        }
        InventoryManager.loadItem(p, 0);
    }
}
