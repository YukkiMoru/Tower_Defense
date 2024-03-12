package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Tower_GUI {

    public void openGUI(Player player) {
        // Create a new inventory with 9 slots
        Inventory gui = Bukkit.createInventory(null, 9, "Tower Defense GUI");

        // Create a new item stack of dirt
        ItemStack item = new ItemStack(Material.DIRT, 1);

        // Get the item's metadata
        ItemMeta meta = item.getItemMeta();

        // Set the item's name
        meta.setDisplayName("Example Item");

        // Apply the updated meta to the item
        item.setItemMeta(meta);

        // Add the item to the GUI
        gui.addItem(item);

        // Open the GUI for the player
        player.openInventory(gui);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Tower Defense GUI")) {
            event.setCancelled(true);
        }
    }
}
