package hd.sphinx.sync.util.scheduler;

import hd.sphinx.sync.api.SyncProfile;
import hd.sphinx.sync.backup.CustomSyncSettings;
import org.bukkit.entity.Player;

public interface Scheduler {

    public void scheduleBackupTask();
    public void cancelBackupTask();

    public void scheduleJoin(Player player);

    public void scheduleExecuteCommands(Player player);

    public void scheduleCompleteLoadEvent(Player player, SyncProfile syncProfile);
    public void scheduleSavingDataEvent(Player player, SyncProfile syncProfile);

    public void scheduleMySQLGeneratePlayer(Player player);
    public void scheduleMySQLSavePlayer(Player player, String invBase64, String ecBase64);
    public void scheduleMySQLSavePlayer(Player player, CustomSyncSettings customSyncSettings);

}
