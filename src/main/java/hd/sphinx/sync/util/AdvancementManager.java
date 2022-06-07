package hd.sphinx.sync.util;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class AdvancementManager {

    public static HashMap<Advancement, Boolean> getAdvancementMap(Player player) {
        Iterator<Advancement> advancements = Bukkit.getServer().advancementIterator();
        HashMap<Advancement, Boolean> returnHashMap = new HashMap<Advancement, Boolean>();

        while (advancements.hasNext()) {
            Advancement a = advancements.next();
            returnHashMap.put(a, player.getAdvancementProgress(a).isDone());
        }
        return returnHashMap;
    }

    public static Advancement getAdvancementByName(String name) {
        Iterator<Advancement> advancements = Bukkit.getServer().advancementIterator();
        while (advancements.hasNext()) {
            Advancement a = advancements.next();
            if (a.getKey().toString().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public static void loadPlayerAdvancements(Player player, String base64) {
        HashMap<Advancement, Boolean> advancements = null;
        try {
            advancements = BukkitSerialization.advancementBooleanHashMapFromBase64(base64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Advancement advancement : advancements.keySet()) {
            if (advancements.get(advancement) && !player.getAdvancementProgress(advancement).isDone()) {
                AdvancementProgress progress = player.getAdvancementProgress(advancement);
                for(String criteria : progress.getRemainingCriteria())
                    progress.awardCriteria(criteria);
            } else if (!advancements.get(advancement) && player.getAdvancementProgress(advancement).isDone()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + player.getName() + " only " + advancement.getKey());
            }
        }
    }
}
