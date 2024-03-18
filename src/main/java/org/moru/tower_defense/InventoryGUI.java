package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryGUI {

    public static Inventory TowerGUI() {
        // Create a new inventory with 9 slots and a custom name
        Inventory gui_test = Bukkit.createInventory(null, 9, "TowerGUI");

        // Create a new item stack of diamonds
        ItemStack diamonds = new ItemStack(Material.DIAMOND, 1);

        // Create a new item meta
        ItemMeta meta = diamonds.getItemMeta();

        // Set the name of the item
        meta.setDisplayName("Click me!");

        // Apply the meta to the item
        diamonds.setItemMeta(meta);

        // Add the item to the inventory
        gui_test.setItem(0, diamonds);

        return gui_test;
    }
}