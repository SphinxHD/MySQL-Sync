package hd.sphinx.sync.backup;

public class CustomSyncSettings {
    private Boolean inventory;
    private Boolean enderchest;
    private Boolean exp;
    private Boolean gamemode;
    private Boolean hunger;
    private Boolean health;
    private Boolean effects;
    private Boolean advancements;
    private Boolean statistics;

    public CustomSyncSettings() {
        inventory = false;
        enderchest = false;
        exp = false;
        gamemode = false;
        hunger = false;
        health = false;
        effects = false;
        advancements = false;
        statistics = false;
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
