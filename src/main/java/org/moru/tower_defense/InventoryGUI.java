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
        Inventory gui_test = CreateInventory("TowerGUI", 27);

        ItemStack Diamond = CreateItem(Material.DIAMOND, 1, "Click me!");
        ItemStack Oak_Planks = CreateItem(Material.OAK_PLANKS, 1, "Archer Tower");
        ItemStack Warden = CreatePlayerHead("Warden", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNmMzY3NGIyZGRjMGVmN2MzOWUzYjljNmI1ODY3N2RlNWNmMzc3ZDJlYjA3M2YyZjNmZTUwOTE5YjFjYTRjOSJ9fX0=");

        // Add the items to the inventory
        gui_test.setItem(2, Oak_Planks);
        gui_test.setItem(25, Diamond);
        gui_test.setItem(26, Warden);  // Add the player head to the inventory

        return gui_test;
    }

    private static Inventory CreateInventory(String name, int size) {
        return Bukkit.createInventory(null, size, name);
    }

    private static ItemStack CreateItem(Material material, int amount, String displayName) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack CreatePlayerHead(String name, String value) {

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "Player_Head");
        profile.getProperties().put("textures", new Property("textures", value));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skullMeta.setDisplayName(name);
        playerHead.setItemMeta(skullMeta);

        return playerHead;

    }
}