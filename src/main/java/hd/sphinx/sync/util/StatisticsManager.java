package hd.sphinx.sync.util;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;

public class StatisticsManager {

    public static HashMap<String, Integer> getStatisticsMap(Player player) {
        HashMap<String, Integer> returnHashMap = new HashMap<String, Integer>();

        for (Statistic statistic : Statistic.values()) {
            if (statistic != Statistic.MINE_BLOCK
                    && statistic != Statistic.USE_ITEM
                    && statistic != Statistic.BREAK_ITEM
                    && statistic != Statistic.CRAFT_ITEM
                    && statistic != Statistic.KILL_ENTITY
                    && statistic != Statistic.ENTITY_KILLED_BY
                    && statistic != Statistic.DROP
                    && statistic != Statistic.PICKUP) {
                returnHashMap.put(statistic.toString(), player.getStatistic(statistic));
            }
        }

        String mineBlock = Statistic.MINE_BLOCK.toString();
        String useItem = Statistic.USE_ITEM.toString();
        String breakItem = Statistic.BREAK_ITEM.toString();
        String craftItem = Statistic.CRAFT_ITEM.toString();
        String drop = Statistic.DROP.toString();
        String pickup = Statistic.PICKUP.toString();
        for (Material material : Material.values()) {
            // MINE_BLOCK only for blocks
            if (material.isBlock()) {
                try {
                    returnHashMap.put(material + "==/=" + mineBlock, player.getStatistic(Statistic.MINE_BLOCK, material));
                } catch (IllegalArgumentException ignored) {}
            }

            // The following stats must only be called for items
            if (!material.isItem()) continue;

            try {
                returnHashMap.put(material + "==/=" + useItem, player.getStatistic(Statistic.USE_ITEM, material));
                returnHashMap.put(material + "==/=" + breakItem, player.getStatistic(Statistic.BREAK_ITEM, material));
                returnHashMap.put(material + "==/=" + craftItem, player.getStatistic(Statistic.CRAFT_ITEM, material));
                returnHashMap.put(material + "==/=" + drop, player.getStatistic(Statistic.DROP, material));
                returnHashMap.put(material + "==/=" + pickup, player.getStatistic(Statistic.PICKUP, material));
            } catch (IllegalArgumentException ignored) {
                // Skip invalid materials
            }
        }

        String killEntity = Statistic.KILL_ENTITY.toString();
        String entityKilledBy = Statistic.ENTITY_KILLED_BY.toString();
        for (EntityType entityType : EntityType.values()) {
            try {
                returnHashMap.put(entityType.toString() + "==/=" + killEntity, player.getStatistic(Statistic.KILL_ENTITY, entityType));
            } catch (Exception ignored) { }
            try {
                returnHashMap.put(entityType.toString() + "==/=" + entityKilledBy, player.getStatistic(Statistic.ENTITY_KILLED_BY, entityType));
            } catch (Exception ignored) { }
        }

        return returnHashMap;
    }

    public static HashMap<String, Integer> loadPlayerStatistics(Player player, String base64) {
        HashMap<String, Integer> statistics = null;
        try {
            statistics = BukkitSerialization.statisticsIntegerHashMapFromBase64(base64);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (String statistic : statistics.keySet()) {
            int value = statistics.get(statistic);

            if (statistic.contains("==/=")) {
                String[] statisticArray = statistic.split("==/=");
                String key = statisticArray[0];
                String statName = statisticArray[1];

                try {
                    Statistic stat = Statistic.valueOf(statName);

                    if (isEntityStatistic(stat)) {
                        // Handle Entity statistics
                        try {
                            EntityType entityType = EntityType.valueOf(key);
                            player.setStatistic(stat, entityType, value);
                        } catch (IllegalArgumentException ignored) { }
                    } else {
                        // Handle Material statistics
                        try {
                            Material material = Material.valueOf(key);
                            // Only set item stats for real items
                            if (requiresItemStatistic(stat) && !material.isItem()) continue;
                            player.setStatistic(stat, material, value);
                        } catch (IllegalArgumentException ignored) { }
                    }
                } catch (IllegalArgumentException ignored) { }
            } else {
                // Handle simple statistics
                try {
                    player.setStatistic(Statistic.valueOf(statistic), value);
                } catch (IllegalArgumentException ignored) { }
            }
        }

        return statistics;
    }

    private static boolean isEntityStatistic(Statistic stat) {
        return stat == Statistic.KILL_ENTITY || stat == Statistic.ENTITY_KILLED_BY;
    }

    private static boolean requiresItemStatistic(Statistic stat) {
        return stat == Statistic.USE_ITEM ||
                stat == Statistic.BREAK_ITEM ||
                stat == Statistic.CRAFT_ITEM ||
                stat == Statistic.DROP ||
                stat == Statistic.PICKUP;
    }
}