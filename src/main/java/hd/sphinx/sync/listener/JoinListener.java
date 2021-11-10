package hd.sphinx.sync.listener;

import hd.sphinx.sync.mysql.ManageData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ManageData.generatePlayer(p);
        ManageData.setInventory(p);
    }
}
