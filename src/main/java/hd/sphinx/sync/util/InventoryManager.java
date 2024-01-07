package hd.sphinx.sync.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InventoryManager {
    private static final int INVENTORY_SIZE = 41;
    
    public static String saveItems(@NotNull Player player, @NotNull PlayerInventory playerInventory) {
        ItemStack[] items = new ItemStack[INVENTORY_SIZE];

        for (int i = 0; i < items.length; i++) {
            items[i] = playerInventory.getItem(i);
        }
        
        return BukkitSerialization.itemStackArrayToBase64(items);
    }

    public static void loadItem(@NotNull String base64, @NotNull Player player) {
        ItemStack[] items = new ItemStack[0];
        try {
            items = BukkitSerialization.itemStackArrayFromBase64(base64);
            if (items == null) return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i <= 40) {
            if (!(items[i] == null)) {
                player.getInventory().setItem(i, items[i]);
            } else {
                player.getInventory().setItem(i, null);
            }
            i++;
        }
    }

    public static String saveEChest(@NotNull Player player) {
        ItemStack[] items = new ItemStack[27];
        int i = 0;
        while (i <= 26) {
            items[i] = player.getEnderChest().getItem(i);
            i++;
        }
        return BukkitSerialization.itemStackArrayToBase64(items);
    }

    public static void loadEChest(@NotNull String base64, @NotNull Player player) {
        ItemStack[] items = new ItemStack[0];
        try {
            items = BukkitSerialization.itemStackArrayFromBase64(base64);
            if (items == null) return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i <= 26) {
            if (items[i] != null) {
                player.getEnderChest().setItem(i, items[i]);
            } else {
                player.getEnderChest().setItem(i, null);
            }
            i++;
        }
    }
}
