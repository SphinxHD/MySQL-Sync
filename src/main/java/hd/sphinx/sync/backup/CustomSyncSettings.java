package hd.sphinx.sync.backup;

public class CustomSyncSettings {
    private Boolean inventory = false;
    private Boolean enderchest = false;
    private Boolean exp = false;
    private Boolean gamemode = false;
    private Boolean hunger = false;
    private Boolean health = false;
    private Boolean effects = false;
    private Boolean advancements = false;
    private Boolean statistics = false;

    public CustomSyncSettings() {

    }

    public void setSyncingInventory(Boolean bool) {
        this.inventory = bool;
    }

    public Boolean isSyncingInventory() {
        return inventory;
    }

    public void setSyncingEnderchest(Boolean bool) {
        this.enderchest = bool;
    }

    public Boolean isSyncingEnderchest() {
        return enderchest;
    }

    public void setSyncingExp(Boolean bool) {
        this.exp = bool;
    }

    public Boolean isSyncingExp() {
        return exp;
    }

    public void setSyncingGamemode(Boolean bool) {
        this.gamemode = bool;
    }

    public Boolean isSyncingGamemode() {
        return gamemode;
    }

    public void setSyncingHunger(Boolean bool) {
        this.hunger = bool;
    }

    public Boolean isSyncingHunger() {
        return hunger;
    }

    public void setSyncingHealth(Boolean bool) {
        this.health = bool;
    }

    public Boolean isSyncingHealth() {
        return health;
    }

    public void setSyncingEffects(Boolean bool) {
        this.effects = bool;
    }

    public Boolean isSyncingEffects() {
        return effects;
    }

    public void setSyncingAdvancements(Boolean bool) {
        this.advancements = bool;
    }

    public Boolean isSyncingAdvancements() {
        return advancements;
    }

    public void setSyncingStatistics(Boolean bool) {
        this.statistics = bool;
    }

    public Boolean isSyncingStatistics() {
        return statistics;
    }
}
