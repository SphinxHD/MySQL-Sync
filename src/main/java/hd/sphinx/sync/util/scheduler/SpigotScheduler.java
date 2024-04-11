package hd.sphinx.sync.util.scheduler;

import hd.sphinx.sync.Main;
import hd.sphinx.sync.MainManageData;
import hd.sphinx.sync.api.SyncProfile;
import hd.sphinx.sync.api.SyncSettings;
import hd.sphinx.sync.api.events.CompletedLoadingPlayerDataEvent;
import hd.sphinx.sync.api.events.SavingPlayerDataEvent;
import hd.sphinx.sync.backup.BackupHandler;
import hd.sphinx.sync.backup.CustomSyncSettings;
import hd.sphinx.sync.mysql.ManageMySQLData;
import hd.sphinx.sync.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpigotScheduler implements Scheduler {

    private Integer backupTaskID;

    @Override
    public void scheduleBackupTask() {
        backupTaskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.main, new Runnable() {
            @Override
            public void run() {
                BackupHandler.handleCycle();
            }
        }, 0L, ConfigManager.config.getInt("settings.backup.backupCycle"));
    }

    @Override
    public void cancelBackupTask() {
        Bukkit.getScheduler().cancelTask(backupTaskID);
    }

    @Override
    public void scheduleJoin(Player player) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.main, new Runnable() {
            @Override
            public void run() {
                MainManageData.loadPlayer(player);
            }
        }, 40l);
    }

    @Override
    public void scheduleExecuteCommands(Player player) {
        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                MainManageData.loadedPlayerData.remove(player);
                for (String command : MainManageData.commandHashMap.get(player)) {
                    player.performCommand(command.replaceFirst("/", ""));
                }
            }
        }, 5l);
    }

    @Override
    public void scheduleCompleteLoadEvent(Player player, SyncProfile syncProfile) {
        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new CompletedLoadingPlayerDataEvent(player, new SyncSettings(), syncProfile));
            }
        }, 1L);
    }

    @Override
    public void scheduleSavingDataEvent(Player player, SyncProfile syncProfile) {
        Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new SavingPlayerDataEvent(player, new SyncSettings(), syncProfile));
            }
        }, 1L);
    }

    @Override
    public void scheduleMySQLGeneratePlayer(Player player) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.main, new Runnable() {
            @Override
            public void run() {
                ManageMySQLData.generatePlayer(player);
            }
        }, 20);
    }

    @Override
    public void scheduleMySQLSavePlayer(Player player, String invBase64, String ecBase64) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.main, new Runnable() {
            @Override
            public void run() {
                ManageMySQLData.savePlayer(player, invBase64, ecBase64);
            }
        }, 20);
    }

    @Override
    public void scheduleMySQLSavePlayer(Player player, CustomSyncSettings customSyncSettings) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.main, new Runnable() {
            @Override
            public void run() {
                ManageMySQLData.savePlayer(player, customSyncSettings);
            }
        }, 20);
    }
}
