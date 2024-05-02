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
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class FoliaScheduler implements Scheduler {

    private final AsyncScheduler asyncScheduler;
    private final GlobalRegionScheduler globalRegionScheduler;

    private ScheduledTask backupTask;

    public FoliaScheduler() {
        asyncScheduler = Bukkit.getAsyncScheduler();
        globalRegionScheduler = Bukkit.getGlobalRegionScheduler();
    }

    @Override
    public void scheduleBackupTask() {
        backupTask = asyncScheduler.runAtFixedRate(Main.main, t -> {
            BackupHandler.handleCycle();
        }, 0L, ConfigManager.config.getInt("settings.backup.backupCycle") / 20, TimeUnit.SECONDS);
    }

    @Override
    public void cancelBackupTask() {
        backupTask.cancel();
    }

    @Override
    public void scheduleJoin(Player player) {
        globalRegionScheduler.runDelayed(Main.main, t -> {
            MainManageData.loadPlayer(player);
        }, 40L);
    }

    @Override
    public void scheduleExecuteCommands(Player player) {
        globalRegionScheduler.runDelayed(Main.main, t -> {
            MainManageData.loadedPlayerData.remove(player);
            for (String command : MainManageData.commandHashMap.get(player)) {
                player.performCommand(command.replaceFirst("/", ""));
            }
        }, 5L);
    }

    @Override
    public void scheduleCompleteLoadEvent(Player player, SyncProfile syncProfile) {
        globalRegionScheduler.runDelayed(Main.main, t -> {
            Bukkit.getPluginManager().callEvent(new CompletedLoadingPlayerDataEvent(player, new SyncSettings(), syncProfile));
        }, 5L);
    }

    @Override
    public void scheduleSavingDataEvent(Player player, SyncProfile syncProfile) {
        globalRegionScheduler.runDelayed(Main.main, t -> {
            Bukkit.getPluginManager().callEvent(new SavingPlayerDataEvent(player, new SyncSettings(), syncProfile));
        }, 5L);
    }

    @Override
    public void scheduleMySQLGeneratePlayer(Player player) {
        asyncScheduler.runDelayed(Main.main, t -> {
            ManageMySQLData.generatePlayer(player);
        }, 1L, TimeUnit.SECONDS);
    }

    @Override
    public void scheduleMySQLSavePlayer(Player player, String invBase64, String ecBase64) {
        asyncScheduler.runDelayed(Main.main, t -> {
            ManageMySQLData.savePlayer(player, invBase64, ecBase64);
        }, 1L, TimeUnit.SECONDS);
    }

    @Override
    public void scheduleMySQLSavePlayer(Player player, CustomSyncSettings customSyncSettings) {
        asyncScheduler.runDelayed(Main.main, t -> {
            ManageMySQLData.savePlayer(player, customSyncSettings);
        }, 1L, TimeUnit.SECONDS);
    }
}
