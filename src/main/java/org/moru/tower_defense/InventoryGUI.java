package org.moru.tower_defense;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class InventoryGUI {
    public static Inventory TowerGUI() {
        Inventory gui_test = createInventory("TowerGUI", 27);

        ItemStack diamonds = createItem(Material.DIAMOND, 1, "Click me!");

        // Create the player head item
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "Warden");
        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNmMzY3NGIyZGRjMGVmN2MzOWUzYjljNmI1ODY3N2RlNWNmMzc3ZDJlYjA3M2YyZjNmZTUwOTE5YjFjYTRjOSJ9fX0="));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skullMeta.setDisplayName("Warden");
        playerHead.setItemMeta(skullMeta);

        // Add the items to the inventory
        gui_test.setItem(0, diamonds);
        gui_test.setItem(1, playerHead);  // Add the player head to the inventory

        return gui_test;
    }

    private static Inventory createInventory(String name, int size) {
        return Bukkit.createInventory(null, size, name);
    }

    private static ItemStack createItem(Material material, int amount, String displayName) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }
}