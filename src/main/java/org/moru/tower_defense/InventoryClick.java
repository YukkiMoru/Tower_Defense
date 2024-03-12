package org.moru.tower_defense;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;

public class InventoryClick {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Set<String> UserTags = event.getWhoClicked().getScoreboardTags();
        if(UserTags.contains("MenuOpen")){
            event.setCancelled(true);
        }
    }
}
