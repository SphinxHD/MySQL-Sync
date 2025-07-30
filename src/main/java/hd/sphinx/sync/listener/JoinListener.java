package hd.sphinx.sync.listener;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.MainManageData;
import hd.sphinx.sync.api.SyncSettings;
import hd.sphinx.sync.api.events.GeneratingPlayerProfileEvent;
import hd.sphinx.sync.api.events.ProcessingLoadingPlayerDataEvent;
import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JoinListener implements Listener {

    // Tracks players who are still loading
    private static final Set<Player> loadingPlayers = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Permission check
        if (ConfigManager.getBoolean("settings.onlySyncPermission") && !player.hasPermission("sync.sync")) {
            return;
        }

        // Prepare data structures for this player
        MainManageData.commandHashMap.put(player, new ArrayList<>());

        // Reset death state
        DeathListener.deadPlayers.remove(player);

        // Mark the player as loading
        loadingPlayers.add(player);

        // If new player
        if (!MainManageData.isPlayerKnown(player)) {
            String generatedMessage = ConfigManager.getColoredString("messages.generated");
            if (!generatedMessage.trim().isEmpty()) {
                player.sendMessage(generatedMessage);
            }

            MainManageData.generatePlayer(player);
            Bukkit.getPluginManager().callEvent(
                    new GeneratingPlayerProfileEvent(player, ConfigManager.getBoolean("settings.usingOldData"))
            );

            if (!ConfigManager.getBoolean("settings.usingOldData")) {
                if (ConfigManager.getBoolean("settings.syncing.enderchest")) player.getEnderChest().clear();
                if (ConfigManager.getBoolean("settings.syncing.inventory")) player.getInventory().clear();
                if (ConfigManager.getBoolean("settings.syncing.exp")) player.setLevel(0);
            }
        } else {
            // Known player: clear inventories if needed
            if (ConfigManager.getBoolean("settings.syncing.enderchest")) player.getEnderChest().clear();
            if (ConfigManager.getBoolean("settings.syncing.inventory")) player.getInventory().clear();
            if (ConfigManager.getBoolean("settings.syncing.exp")) player.setLevel(0);
        }

        // Send loading message
        if (ConfigManager.getBoolean("settings.sending.loading")) {
            String generatedMessage = ConfigManager.getColoredString("messages.loading");
            if (!generatedMessage.trim().isEmpty()) {
                player.sendMessage(generatedMessage);
            }
        }

        // Trigger loading event
        Bukkit.getPluginManager().callEvent(new ProcessingLoadingPlayerDataEvent(player, new SyncSettings()));

        // Schedule async join data loading
        Main.schedulerManager.getScheduler().scheduleJoin(player);

        // When scheduleJoin completes loading, call this:
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> {
            // Player is fully loaded
            MainManageData.loadedPlayerData.add(player);
            loadingPlayers.remove(player);
        }, 40L); // ~2 seconds delay (adjust based on your loading time)
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        // Cancel only if player is still loading or dead
        if (loadingPlayers.contains(player) || DeathListener.deadPlayers.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        // Cancel if still loading or dead
        if (loadingPlayers.contains(player) || DeathListener.deadPlayers.contains(player)) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.AIR) {
                event.setCancelled(true);
            }
        }
    }
}
