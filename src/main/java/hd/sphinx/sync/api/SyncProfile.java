package hd.sphinx.sync.api;

import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;

public class SyncProfile {

    private Player owner;

    private PlayerInventory playerInventory;
    private Inventory enderChest;
    private Integer exp;
    private GameMode gameMode;
    private Integer hunger;
    private Double health;
    private Collection<PotionEffect> potionEffects;
    private HashMap<Advancement, Boolean> advancements;
    private HashMap<String, Integer> rawStatistics;

    public SyncProfile(Player owner) {
        this.owner = owner;
    }

    public void setPlayerInventory(PlayerInventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public void setEnderChest(Inventory enderChest) {
        this.enderChest = enderChest;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public void setPotionEffects(Collection<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public void setAdvancements(HashMap<Advancement, Boolean> advancements) {
        this.advancements = advancements;
    }

    public void setRawStatistics(HashMap<String, Integer> rawStatistics) {
        this.rawStatistics = rawStatistics;
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public Inventory getEnderChest() {
        return enderChest;
    }

    public Integer getExp() {
        return exp;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Integer getHunger() {
        return hunger;
    }

    public Double getHealth() {
        return health;
    }

    public Collection<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public HashMap<Advancement, Boolean> getAdvancements() {
        return advancements;
    }

    public HashMap<String, Integer> getRawStatistics() {
        return rawStatistics;
    }
}
