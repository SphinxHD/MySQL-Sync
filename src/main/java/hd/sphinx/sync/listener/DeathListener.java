package hd.sphinx.sync.listener;

import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;

public class DeathListener implements Listener {

    public static ArrayList<Player> deadPlayers = new ArrayList<Player>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (ConfigManager.getBoolean("settings.onlySyncPermission") && !event.getEntity().hasPermission("sync.sync")) return;
        deadPlayers.add(event.getEntity());
    }

    public static void triggerRespawn(Player player) {
        if (ConfigManager.getBoolean("settings.onlySyncPermission").booleanValue() && !player.hasPermission("sync.sync"))
            return;
        deadPlayers.remove(player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        triggerRespawn(event.getPlayer());
    }
}
