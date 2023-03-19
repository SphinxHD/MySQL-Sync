package hd.sphinx.sync.listener;

import hd.sphinx.sync.mysql.ManageData;
import hd.sphinx.sync.util.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!ManageData.loadInventory.contains(player)) {
            ManageData.savePlayer(player, InventoryManager.saveItems(player, player.getInventory()), InventoryManager.saveEChest(player));
        } else {
            ManageData.loadInventory.remove(player);
        }
    }
}
