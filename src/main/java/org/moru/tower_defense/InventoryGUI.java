package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryGUI {

    public static Inventory TowerGUI() {
        Inventory gui_test = createInventory("TowerGUI", 27);
        ItemStack diamonds = createItem(Material.DIAMOND, 1, "Click me!");
        gui_test.setItem(0, diamonds);
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