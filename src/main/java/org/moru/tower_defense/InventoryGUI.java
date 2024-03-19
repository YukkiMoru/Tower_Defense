package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class InventoryGUI {

    public static Inventory TowerGUI() {
        Inventory gui_test = createInventory("TowerGUI", 27);
        ItemStack diamonds = createItem(Material.DIAMOND, 1, "Click me!");

        // Create the player head item
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("1531857479-78233159-1681038604-1859460671")));
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