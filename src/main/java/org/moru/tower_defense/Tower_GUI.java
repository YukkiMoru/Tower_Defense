package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Tower_GUI implements Listener {
    private final Inventory gui;

    public Tower_GUI() {
        // Create a new inventory with 9 slots
        gui = Bukkit.createInventory(null, 9, "Tower Defense GUI");

        // Initialize the items in the inventory
        initializeItems();
    }

    private void initializeItems() {
        gui.addItem(createGuiItem(Material.DIRT, "Example Item", "§aFirst line of the lore", "§bSecond line of the lore"));
    }

    private ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the item's name
        meta.setDisplayName(name);

        // Set the item's lore
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openGUI(final HumanEntity ent) {
        ent.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!event.getInventory().equals(gui)) return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) event.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + event.getRawSlot());
    }

    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent event) {
        if (event.getInventory().equals(gui)) {
            event.setCancelled(true);
        }
    }
}