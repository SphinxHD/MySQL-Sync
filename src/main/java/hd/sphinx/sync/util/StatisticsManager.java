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
            if (material.isBlock()) {
                returnHashMap.put(material.toString() + "==/=" + mineBlock, player.getStatistic(Statistic.MINE_BLOCK, material));
            }
            returnHashMap.put(material.toString() + "==/=" + useItem, player.getStatistic(Statistic.USE_ITEM, material));
            returnHashMap.put(material.toString() + "==/=" + breakItem, player.getStatistic(Statistic.BREAK_ITEM, material));
            returnHashMap.put(material.toString() + "==/=" + craftItem, player.getStatistic(Statistic.CRAFT_ITEM, material));
            returnHashMap.put(material.toString() + "==/=" + drop, player.getStatistic(Statistic.DROP, material));
            returnHashMap.put(material.toString() + "==/=" + pickup, player.getStatistic(Statistic.PICKUP, material));
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

    public static void loadPlayerStatistics(Player player, String base64) {
        HashMap<String, Integer> statistics = null;
        try {
            statistics = BukkitSerialization.statisticsIntegerHashMapFromBase64(base64);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (String statistic : statistics.keySet()) {
            if (statistic.contains("==/=") && statistic.contains("entity")) {
                String[] statisticArray = statistic.split("==/=");
                player.setStatistic(Statistic.valueOf(statisticArray[1]), EntityType.valueOf(statisticArray[0]), statistics.get(statistic));
            } else if (statistic.contains("==/=")) {
                String[] statisticArray = statistic.split("==/=");
                player.setStatistic(Statistic.valueOf(statisticArray[1]), Material.valueOf(statisticArray[0]), statistics.get(statistic));
            } else {
                player.setStatistic(Statistic.valueOf(statistic), statistics.get(statistic));
            }
        }
    }
}
