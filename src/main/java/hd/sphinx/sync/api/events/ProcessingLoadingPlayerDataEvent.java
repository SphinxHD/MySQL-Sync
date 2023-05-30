package hd.sphinx.sync.api.events;

import hd.sphinx.sync.api.SyncSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProcessingLoadingPlayerDataEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private SyncSettings syncSettings;

    public ProcessingLoadingPlayerDataEvent(Player player, SyncSettings syncSettings) {
        this.player = player;
        this.syncSettings = syncSettings;
     }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public SyncSettings getSyncSettings() {
        return syncSettings;
    }
}
