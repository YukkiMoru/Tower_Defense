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
    private JavaPlugin plugin;
    private boolean cooldown = false;

    private List<Tower> towers = new ArrayList<>();

    public Platform_Listener(JavaPlugin plugin) {
        this.plugin = plugin;

        // Schedule the attackMobs method to be called periodically
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (Tower tower : towers) {
                    tower.attackMobs();
                }
            }
        }, 0L, 20L); // 20 ticks = 1 second
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Platform_Manager platformManager = Platform_Manager.getInstance();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !cooldown) {
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

    public void setCooldown() {
        cooldown = true;
        System.out.println("Cooldown started"); // Debug message

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            cooldown = false;
            System.out.println("Cooldown ended"); // Debug message
        }, 20L); // 2 ticks cooldown (20 ticks/sec * 2 sec = 40 ticks)
    }
}