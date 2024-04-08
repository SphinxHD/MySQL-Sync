package hd.sphinx.sync.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GeneratingPlayerProfileEvent extends Event {


    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;
    private Boolean loadingFromExistingData;

    public GeneratingPlayerProfileEvent(Player player, Boolean loadingFromExistingData) {
        this.player = player;
        this.loadingFromExistingData = loadingFromExistingData;
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

    public Boolean isLoadingFromExistingData() {
        return loadingFromExistingData;
    }
}
