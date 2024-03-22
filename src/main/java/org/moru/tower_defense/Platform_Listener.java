package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


public class Platform_Listener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Platform_Manager platformManager = Platform_Manager.getInstance();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.CHERRY_PLANKS) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                    Location Edgelocation = platformManager.Platform(event.getClickedBlock().getLocation(), 3, 3, Material.CHERRY_PLANKS, event);
                    if (Edgelocation != null) {
                        Player player = event.getPlayer();
                        Inventory gui = InventoryGUI.TowerGUI();
                        player.openInventory(gui);
                    }
                }
            }
        }
    }
}