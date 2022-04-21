package hd.sphinx.sync.listener;

import hd.sphinx.sync.mysql.ManageData;
import hd.sphinx.sync.mysql.MySQL;
import hd.sphinx.sync.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        ManageData.savePlayer(p, InventoryManager.saveItems(p, p.getInventory()), InventoryManager.saveEChest(p));
    }
}
