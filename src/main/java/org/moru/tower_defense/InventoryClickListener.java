package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    private int TowerID;
    private Tower_Manager towerManager;

    public InventoryClickListener() {
        towerManager = new Tower_Manager();
        TowerID = towerManager.GetLastTowerID() + 1;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getViewers().contains(event.getWhoClicked())) {
            if (event.getView().getTitle().equals("TowerGUI")) {
                event.setCancelled(true);
                Platform_Manager platformManager = Platform_Manager.getInstance();
                Player player = (Player) event.getWhoClicked();
                //send message
                player.sendMessage("You clicked at slot " + event.getSlot());

                Location Edgelocation = platformManager.getEdgelocation();
                player.sendMessage("EdgeLocation: " + Edgelocation);
                switch (event.getSlot()) {
                    case 2: // Oak_Planks
                        // Construct the tower
                        Construction construction = new Construction();
                        construction.SummonStructure(Edgelocation, "test_tower");
                        construction.GetSizeStructure("test_tower");
                        // save the tower data
                        towerManager.WriteTowerDatabase(TowerID, "Archer", 3, 1);
                        player.sendMessage("Count: " + TowerID);
                        player.sendMessage("Tower constructed");
                        player.sendMessage("Tower size: " + construction.GetSizeStructure("test_tower").x + " " + construction.GetSizeStructure("test_tower").y + " " + construction.GetSizeStructure("test_tower").z);
                        TowerID++;
                        break;
                    case 25: // Diamond
                        // Code to execute when Diamond is clicked
                        break;
                    case 26: // Warden
                        // Code to execute when Warden is clicked
                        break;
                    default:
                        break;
                }
            }
        }
    }
}