package hd.sphinx.sync.util;

import hd.sphinx.sync.mysql.ManageData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InventoryManager {

    public static void saveItem(@NotNull Player player, @NotNull Integer slot) {
        ItemStack[] items = new ItemStack[1];
        items[0] = player.getInventory().getItem(slot);
        ManageData.savePlayer(player, BukkitSerialization.itemStackArrayToBase64(items));
    }

    public static void loadItem(@NotNull Player player, @NotNull Integer slot) {
        ItemStack[] items = new ItemStack[0];
        try {
            items = BukkitSerialization.itemStackArrayFromBase64(ManageData.loadPlayer(player));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.getInventory().setItem(slot, items[0]);
    }

    public static void generatePlayer(@NotNull Player player) {
        ManageData.generatePlayer(player);
    }
}
