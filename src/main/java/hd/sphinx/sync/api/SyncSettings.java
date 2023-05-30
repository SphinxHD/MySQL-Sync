package hd.sphinx.sync.api;

import hd.sphinx.sync.util.ConfigManager;

public class SyncSettings {

    private Boolean inventory;
    private Boolean enderchest;
    private Boolean exp;
    private Boolean gamemode;
    private Boolean hunger;
    private Boolean health;
    private Boolean effects;
    private Boolean advancements;
    private Boolean statistics;

    public SyncSettings() {
        inventory = ConfigManager.getBoolean("settings.syncing.inventory");
        enderchest = ConfigManager.getBoolean("settings.syncing.enderchest");
        exp = ConfigManager.getBoolean("settings.syncing.exp");
        gamemode = ConfigManager.getBoolean("settings.syncing.gamemode");
        hunger = ConfigManager.getBoolean("settings.syncing.hunger");
        health = ConfigManager.getBoolean("settings.syncing.health");
        effects = ConfigManager.getBoolean("settings.syncing.effects");
        advancements = ConfigManager.getBoolean("settings.syncing.advancements");
        statistics = ConfigManager.getBoolean("settings.syncing.statistics");
    }

    public Boolean isSyncingInventory() {
        return inventory;
    }

    public Boolean isSyncingEnderchest() {
        return enderchest;
    }

    public Boolean isSyncingExp() {
        return exp;
    }

    public Boolean isSyncingGamemode() {
        return gamemode;
    }

    public Boolean isSyncingHunger() {
        return hunger;
    }

    public Boolean isSyncingHealth() {
        return health;
    }

    public Boolean isSyncingEffects() {
        return effects;
    }

    public Boolean isSyncingAdvancements() {
        return advancements;
    }

    public Boolean isSyncingStatistics() {
        return statistics;
    }
}
