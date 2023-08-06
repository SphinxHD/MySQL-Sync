package hd.sphinx.sync.backup;

import hd.sphinx.sync.util.AdvancementManager;
import hd.sphinx.sync.util.StatisticsManager;
import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class CachePlayer {

    private Player player;

    private Inventory inventory;
    private Inventory enderchest;
    private Integer exp;
    private GameMode gamemode;
    private Integer hunger;
    private Double health;
    private Collection<PotionEffect> effects;
    private HashMap<Advancement, Boolean> advancements;
    private HashMap<String, Integer> statistics;

    public CachePlayer(Player player) {
        this.player = player;
        this.inventory = player.getInventory();
        this.enderchest = player.getEnderChest();
        this.exp = player.getLevel();
        this.gamemode = player.getGameMode();
        this.hunger = player.getFoodLevel();
        this.health = player.getHealth();
        this.effects = player.getActivePotionEffects();
        this.advancements = AdvancementManager.getAdvancementMap(player);
        this.statistics = StatisticsManager.getStatisticsMap(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Boolean compareInventory(Inventory inventory) {
        return this.inventory.iterator().equals(inventory.iterator());
    }

    public void setEnderchest(Inventory enderchest) {
        this.enderchest = enderchest;
    }

    public Inventory getEnderchest() {
        return enderchest;
    }

    public Boolean compareEnderchest(Inventory enderchest) {
        return this.enderchest.iterator().equals(enderchest.iterator());
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getExp() {
        return exp;
    }

    public Boolean compareExp(Integer exp) {
        return Objects.equals(this.exp, exp);
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public Boolean compareGamemode(GameMode gamemode) {
        return this.gamemode == gamemode;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }

    public Integer getHunger() {
        return hunger;
    }

    public Boolean compareHunger(Integer hunger) {
        return Objects.equals(this.hunger, hunger);
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public Double getHealth() {
        return health;
    }

    public Boolean compareHealth(Double health) {
        return Objects.equals(this.health, health);
    }

    public void setEffects(Collection<PotionEffect> effects) {
        this.effects = effects;
    }

    public Collection<PotionEffect> getEffects() {
        return effects;
    }

    public Boolean compareEffects(Collection<PotionEffect> effects) {
        return this.effects == effects;
    }

    public void setAdvancements(HashMap<Advancement, Boolean> advancements) {
        this.advancements = advancements;
    }

    public HashMap<Advancement, Boolean> getAdvancements() {
        return advancements;
    }

    public Boolean compareAdvancements(HashMap<Advancement, Boolean> advancements) {
        return this.advancements == advancements;
    }

    public void setStatistics(HashMap<String, Integer> statistics) {
        this.statistics = statistics;
    }

    public HashMap<String, Integer> getStatistics() {
        return statistics;
    }

    public Boolean compareStatistics(HashMap<String, Integer> statistics) {
        return this.statistics == statistics;
    }
}
