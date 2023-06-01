package hd.sphinx.sync.mongo;

import com.mongodb.*;
import com.mongodb.client.model.ReplaceOptions;
import hd.sphinx.sync.Main;
import hd.sphinx.sync.MainManageData;
import hd.sphinx.sync.api.SyncProfile;
import hd.sphinx.sync.api.SyncSettings;
import hd.sphinx.sync.api.events.CompletedLoadingPlayerDataEvent;
import hd.sphinx.sync.api.events.SavingPlayerDataEvent;
import hd.sphinx.sync.listener.DeathListener;
import hd.sphinx.sync.util.*;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class ManageMongoData {

    public static Boolean isPlayerInDB(Player player) {
        if (!MongoDB.isConnected()) {
            MongoDB.connectMongoDB();
        }
        Document document = (Document) MongoDB.getMongoCollection().find(eq("_id", player.getUniqueId().toString())).first();
        return document != null;
    }

    public static void generatePlayer(Player player) {
        if (!MongoDB.isConnected()) {
            MongoDB.connectMongoDB();
        }
        if (isPlayerInDB(player)) return;
        try {
            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("player_name", player.getName());
            java.util.Date dateNow = new Date();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MM.dd.yyyy G 'at' HH:mm:ss z");
            document.append("last_joined", simpleDateFormat.format(dateNow));
            MongoDB.getMongoCollection().insertOne(document);
        } catch (Exception ignored) {
            ignored.printStackTrace();
            Main.logger.warning("Something went wrong with registering a Player!");
            if (ConfigManager.getBoolean("settings.sending.error")) {
                player.sendMessage(ConfigManager.getColoredString("messages.error"));
            }
        }
    }

    public static void loadPlayer(Player player) {
        if (!MongoDB.isConnected()) {
            MongoDB.connectMongoDB();
        }
        try {
            SyncProfile syncProfile = new SyncProfile(player);
            Document document = (Document) MongoDB.getMongoCollection().find(eq("_id", player.getUniqueId().toString())).first();
            String result;
            result = (String) document.get("inventory");
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.inventory")) {
                    InventoryManager.loadItem(result, player);
                    syncProfile.setPlayerInventory(player.getInventory());
                    result = null;
                }
            } catch (Exception ignored) { }
            result = (String) document.get("gamemode");
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.gamemode")) {
                    player.setGameMode(GameMode.valueOf(result));
                    syncProfile.setGameMode(GameMode.valueOf(result));
                    result = null;
                }
            } catch (Exception ignored) { }
            result = String.valueOf(document.getDouble("health"));
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.health")) {
                    player.setHealth(Double.parseDouble(result));
                    syncProfile.setHealth(Double.parseDouble(result));
                    result = null;
                }
            } catch (Exception ignored) { }
            result = String.valueOf(document.getInteger("food"));
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.hunger")) {
                    player.setFoodLevel(Integer.parseInt(result));
                    syncProfile.setHunger(Integer.parseInt(result));
                    result = null;
                }
            } catch (Exception ignored) { }
            result = String.valueOf(document.getInteger("exp"));
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.exp")) {
                    player.setLevel(Integer.parseInt(result));
                    syncProfile.setExp(Integer.parseInt(result));
                    result = null;
                }
            } catch (Exception ignored) { }
            result = (String) document.get("enderchest");
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.enderchest")) {
                    InventoryManager.loadEChest(result, player);
                    syncProfile.setEnderChest(player.getEnderChest());
                    result = null;
                }
            } catch (Exception ignored) { }
            result = (String) document.get("effects");
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.effects")) {
                    Collection<PotionEffect> potionEffectCollection = null;
                    potionEffectCollection = Arrays.asList(BukkitSerialization.potionEffectArrayFromBase64(result));
                    player.addPotionEffects(potionEffectCollection);
                    syncProfile.setPotionEffects(potionEffectCollection);
                    result = null;
                }
            } catch (Exception ignored) { }
            result = (String) document.get("advancements");
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.advancements")) {
                    syncProfile.setAdvancements(AdvancementManager.loadPlayerAdvancements(player, result));
                    result = null;
                }
            } catch (Exception ignored) { }
            result = (String) document.get("statistics");
            try {
                if (result != null && ConfigManager.getBoolean("settings.syncing.statistics")) {
                    syncProfile.setRawStatistics(StatisticsManager.loadPlayerStatistics(player, result));
                }
            } catch (Exception ignored) { }
            player.sendMessage(ConfigManager.getColoredString("messages.loaded"));
            Bukkit.getScheduler().runTaskLater(Main.main, new Runnable() {
                @Override
                public void run() {
                    MainManageData.loadedPlayerData.remove(player);
                }
            }, 5l);
            Bukkit.getPluginManager().callEvent(new CompletedLoadingPlayerDataEvent(player, new SyncSettings(), syncProfile));
        } catch (Exception ignored) {
            Main.logger.warning("Something went wrong with loading a Player!");
            if (ConfigManager.getBoolean("settings.sending.error")) {
                player.sendMessage(ConfigManager.getColoredString("messages.error"));
            }
        }
    }

    public static void savePlayer(Player player, String invBase64, String ecBase64) {
        if (MainManageData.loadedPlayerData.contains(player)) return;
        if (!MongoDB.isConnected()) {
            MongoDB.connectMongoDB();
        }
        try {
            SyncProfile syncProfile = new SyncProfile(player);
            Document document = new Document();
            document.append("_id", player.getUniqueId().toString());
            document.append("player_name", player.getName());
            java.util.Date dateNow = new Date();
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MM.dd.yyyy G 'at' HH:mm:ss z");
            document.append("last_joined", simpleDateFormat.format(dateNow));
            if (ConfigManager.getBoolean("settings.syncing.inventory")) {
                document.append("inventory", invBase64);
                syncProfile.setPlayerInventory(player.getInventory());
            }
            if (ConfigManager.getBoolean("settings.syncing.gamemode")) {
                document.append("gamemode", String.valueOf(player.getGameMode()));
                syncProfile.setGameMode(player.getGameMode());
            }
            if (ConfigManager.getBoolean("settings.syncing.health")) {
                document.append("health", player.getHealth());
                syncProfile.setHealth(player.getHealth());
            }
            if (ConfigManager.getBoolean("settings.syncing.hunger")) {
                document.append("food", player.getFoodLevel());
                syncProfile.setHunger(player.getFoodLevel());
            }
            if (ConfigManager.getBoolean("settings.syncing.enderchest")) {
                document.append("enderchest", ecBase64);
                syncProfile.setEnderChest(player.getEnderChest());
            }
            if (ConfigManager.getBoolean("settings.syncing.exp")) {
                document.append("exp", player.getLevel());
                syncProfile.setExp(player.getLevel());
            }
            if (ConfigManager.getBoolean("settings.syncing.effects")) {
                Collection<PotionEffect> effectCollection = player.getActivePotionEffects();
                PotionEffect[] effectArray = new ArrayList<PotionEffect>(effectCollection).toArray(new PotionEffect[0]);
                document.append("effects", BukkitSerialization.potionEffectArrayToBase64(effectArray));
                syncProfile.setPotionEffects(effectCollection);
            }
            if (ConfigManager.getBoolean("settings.syncing.advancements")) {
                HashMap<Advancement, Boolean> advancementMap = AdvancementManager.getAdvancementMap(player);
                document.append("advancements", BukkitSerialization.advancementBooleanHashMapToBase64(advancementMap));
                syncProfile.setAdvancements(advancementMap);
            }
            if (ConfigManager.getBoolean("settings.syncing.statistics")) {
                HashMap<String, Integer> statisticsMap = StatisticsManager.getStatisticsMap(player);
                document.append("statistics", BukkitSerialization.statisticsIntegerHashMapToBase64(statisticsMap));
                syncProfile.setRawStatistics(statisticsMap);
            }
            ReplaceOptions replaceOptions = new ReplaceOptions().upsert(true);
            MongoDB.getMongoCollection().replaceOne(eq("_id", document.get("_id")), document, replaceOptions);
            Bukkit.getPluginManager().callEvent(new SavingPlayerDataEvent(player, new SyncSettings(), syncProfile));
        } catch (Exception ignored) {
            Main.logger.warning("Something went wrong with saving a Player!");
            if (ConfigManager.getBoolean("settings.sending.error")) {
                player.sendMessage(ConfigManager.getColoredString("messages.error"));
            }
        }
    }
}
