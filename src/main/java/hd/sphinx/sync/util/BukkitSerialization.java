package hd.sphinx.sync.util;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BukkitSerialization {

    /**
     * A method to serialize an {@link ItemStack} array to Base64 String.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                if (item != null) {
                    dataOutput.writeObject(item.serialize());
                } else {
                    dataOutput.writeObject(null);
                }
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            if (data == null) return null;
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int Index = 0; Index < items.length; Index++) {
                Map<String, Object> stack = (Map<String, Object>) dataInput.readObject();

                if (stack != null) {
                    items[Index] = ItemStack.deserialize(stack);
                } else {
                    items[Index] = null;
                }
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * A method to serialize an {@link PotionEffect} array to Base64 String.
     *
     * @param effects to turn into a Base64 String.
     * @return Base64 string of the effects.
     * @throws IllegalStateException
     */
    public static String potionEffectArrayToBase64(PotionEffect[] effects) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(effects.length);

            for (PotionEffect effect : effects) {
                dataOutput.writeObject(effect.getType().getName() + ";"
                        + effect.getDuration() + ";"
                        + effect.getAmplifier()  + ";"
                        +  effect.isAmbient() + ";"
                        + effect.hasParticles() + ";"
                        + effect.hasIcon());
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save potion effects.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * @param data Base64 string to convert to PotionEffect array.
     * @return PotionEffect array created from the Base64 string.
     * @throws IOException
     */
    public static PotionEffect[] potionEffectArrayFromBase64(String data) throws IOException {
        try {
            if (data == null) return null;
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            PotionEffect[] potions = new PotionEffect[dataInput.readInt()];

            for (int Index = 0; Index < potions.length; Index++) {
                String potion = (String) dataInput.readObject();
                String[] potionArray = potion.split(";");

                if (potion != null) {
                    potions[Index] = new PotionEffect(PotionEffectType.getByName(potionArray[0]),
                            Integer.parseInt(potionArray[1]),
                            Integer.parseInt(potionArray[2]),
                            Boolean.getBoolean(potionArray[3]),
                            Boolean.getBoolean(potionArray[4]),
                            Boolean.getBoolean(potionArray[5]));
                } else {
                    potions[Index] = null;
                }
            }

            dataInput.close();
            return potions;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * A method to serialize an {@link Advancement} and {@link Boolean} HashMap to Base64 String.
     *
     * @param advancements to turn into a Base64 String.
     * @return Base64 string of the advancements and booleans.
     * @throws IllegalStateException
     */
    public static String advancementBooleanHashMapToBase64(HashMap<Advancement, Boolean> advancements) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(advancements.size());

            for (Advancement advancement : advancements.keySet()) {
                dataOutput.writeObject(advancement.getKey() + ";"
                        + advancements.get(advancement));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save advancements.", e);
        }
    }

    /**
     * Gets an HashMap out of Advancements and Booleans from Base64 string.
     *
     * @param data Base64 string to convert to HashMap out of Advancements and Booleans.
     * @return HashMap out of Advancements and Booleans created from the Base64 string.
     * @throws IOException
     */
    public static HashMap<Advancement, Boolean> advancementBooleanHashMapFromBase64(String data) throws IOException {
        try {
            if (data == null) return null;
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            HashMap<Advancement, Boolean> advancements = new HashMap<Advancement, Boolean>();
            int InputInt = dataInput.readInt();

            for (int Index = 0; Index < InputInt; Index++) {
                String advancement = (String) dataInput.readObject();
                String[] advancementArray = advancement.split(";");

                advancements.put(AdvancementManager.getAdvancementByName(advancementArray[0]), Boolean.valueOf(advancementArray[1]));
            }

            dataInput.close();
            return advancements;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * A method to serialize an {@link String} and {@link Integer} HashMap to Base64 String.
     *
     * @param statistics to turn into a Base64 String.
     * @return Base64 string of the advancements and booleans.
     * @throws IllegalStateException
     */
    public static String statisticsIntegerHashMapToBase64(HashMap<String, Integer> statistics) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(statistics.size());

            for (String statistic : statistics.keySet()) {
                dataOutput.writeObject(statistic + ";"
                        + statistics.get(statistic));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save statistics.", e);
        }
    }

    /**
     * Gets an HashMap out of Strings and Integers from Base64 string.
     *
     * @param data Base64 string to convert to HashMap out of Strings and Integers.
     * @return HashMap out of Strings and Integers created from the Base64 string.
     * @throws IOException
     */
    public static HashMap<String, Integer> statisticsIntegerHashMapFromBase64(String data) throws IOException {
        try {
            if (data == null) return null;
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            HashMap<String, Integer> statistics = new HashMap<String, Integer>();
            int InputInt = dataInput.readInt();

            for (int Index = 0; Index < InputInt; Index++) {
                String statistic = (String) dataInput.readObject();
                String[] statisticArray = statistic.split(";");

                statistics.put(statisticArray[0], Integer.valueOf(statisticArray[1]));
            }

            dataInput.close();
            return statistics;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }
}
