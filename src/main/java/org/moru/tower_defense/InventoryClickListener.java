package org.moru.tower_defense;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getViewers().contains(event.getWhoClicked())) {
            event.setCancelled(true);
        }
    }
}