package hd.sphinx.sync.backup;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.MainManageData;
import hd.sphinx.sync.util.AdvancementManager;
import hd.sphinx.sync.util.ConfigManager;
import hd.sphinx.sync.util.StatisticsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BackupHandler {

    public static Integer id;

    public static void initialize() {
        Main.schedulerManager.getScheduler().scheduleBackupTask();
    }

    public static void handleCycle() {
        Boolean inventory = getSaveBool("inventory");
        Boolean enderchest = getSaveBool("enderchest");
        Boolean exp = getSaveBool("exp");
        Boolean gamemode = getSaveBool("gamemode");
        Boolean hunger = getSaveBool("hunger");
        Boolean health = getSaveBool("health");
        Boolean effects = getSaveBool("effects");
        Boolean advancements = getSaveBool("advancements");
        Boolean statistics = getSaveBool("statistics");
        HashMap<Player, CachePlayer> playerCache = new HashMap<Player, CachePlayer>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            CachePlayer cachePlayer;
            if (playerCache.containsKey(player)) {
                cachePlayer = playerCache.get(player);
            } else {
                cachePlayer = new CachePlayer(player);
                playerCache.put(player, cachePlayer);
            }
            CustomSyncSettings customSyncSettings = new CustomSyncSettings();
            if (inventory && !cachePlayer.compareInventory(player.getInventory())) {
                cachePlayer.setInventory(player.getInventory());
                customSyncSettings.setSyncingInventory(true);
            }
            if (enderchest && !cachePlayer.compareEnderchest(player.getEnderChest())) {
                cachePlayer.setEnderchest(player.getEnderChest());
                customSyncSettings.setSyncingEnderchest(true);
            }
            if (exp && !cachePlayer.compareExp(player.getLevel())) {
                cachePlayer.setExp(player.getLevel());
                customSyncSettings.setSyncingExp(true);
            }
            if (gamemode && !cachePlayer.compareGamemode(player.getGameMode())) {
                cachePlayer.setGamemode(player.getGameMode());
                customSyncSettings.setSyncingGamemode(true);
            }
            if (hunger && !cachePlayer.compareHunger(player.getFoodLevel())) {
                cachePlayer.setHunger(player.getFoodLevel());
                customSyncSettings.setSyncingHunger(true);
            }
            if (health && !cachePlayer.compareHealth(player.getHealth())) {
                cachePlayer.setHealth(player.getHealth());
                customSyncSettings.setSyncingHealth(true);
            }
            if (effects && !cachePlayer.compareEffects(player.getActivePotionEffects())) {
                cachePlayer.setEffects(player.getActivePotionEffects());
                customSyncSettings.setSyncingEffects(true);
            }
            if (advancements && !cachePlayer.compareAdvancements(AdvancementManager.getAdvancementMap(player))) {
                cachePlayer.setAdvancements(AdvancementManager.getAdvancementMap(player));
                customSyncSettings.setSyncingAdvancements(true);
            }
            if (statistics && !cachePlayer.compareStatistics(StatisticsManager.getStatisticsMap(player))) {
                cachePlayer.setStatistics(StatisticsManager.getStatisticsMap(player));
                customSyncSettings.setSyncingStatistics(true);
            }
            MainManageData.savePlayer(player, customSyncSettings);
        }
    }

    public static void shutdown() {
        Main.schedulerManager.getScheduler().cancelBackupTask();
    }

    public static Boolean getSaveBool(String key) {
        return ConfigManager.getBoolean("settings.syncing." + key) && ConfigManager.getBoolean("settings.backup.values." + key);
    }
}
