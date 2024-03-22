package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;


public class ListenerBlock implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ManagerPlatform managerPlatform = ManagerPlatform.getInstance();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.CHERRY_PLANKS) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                    Location Edgelocation = managerPlatform.Platform(event.getClickedBlock().getLocation(), 3, 3, Material.CHERRY_PLANKS, event);
                    if (Edgelocation != null) {
                        Player player = event.getPlayer();
                        Inventory gui = InventoryGUI.TowerGUI();
                        player.openInventory(gui);
                    }
                }
            }

            // Get the clicked block's coordinates
            Location clickedBlockLocation = event.getClickedBlock().getLocation();

            // get an instance of SQLiteManagerTower
            SQLiteManagerTower sqliteManagerTower = new SQLiteManagerTower();

            // Get the TowerID from the SQLite database
            int TowerID = sqliteManagerTower.GetTowerID(clickedBlockLocation);

            Player player = (Player) event.getPlayer();
            player.sendMessage("TowerID " + TowerID + " がクリックされました!");

        }
    }
}