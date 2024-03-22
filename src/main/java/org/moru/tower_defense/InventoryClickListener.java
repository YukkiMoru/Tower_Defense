package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickListener implements Listener {
    private List<Tower> towers = new ArrayList<>();
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
                // GUI内のアイテムを取った時そのイベントをキャンセル
                event.setCancelled(true);
                Platform_Manager platformManager = Platform_Manager.getInstance();
                Player player = (Player) event.getWhoClicked();
                //send message
                Location Edgelocation = platformManager.getEdgelocation();
                player.sendMessage("You clicked at slot " + event.getSlot());
                player.sendMessage("EdgeLocation: " + Edgelocation);
                switch (event.getSlot()) {
                    case 2: // Oak_Planks
                        // タワーの建設
                        Construction construction = new Construction();
                        construction.SummonStructure(Edgelocation, "test_tower");

                        Construction.Size size = construction.GetSizeStructure("test_tower");

                        //　攻撃用タワー(ArmorStand)の設定
                        Location spawnLocation = Edgelocation;
                        spawnLocation.setX(spawnLocation.getX() + (double) (size.x / 2) - 0.5);
                        spawnLocation.setY(spawnLocation.getY() + size.y + 1.0);
                        spawnLocation.setZ(spawnLocation.getZ() + (double) (size.z / 2) - 0.5);
                        ArmorStand armorStand = Edgelocation.getWorld().spawn(spawnLocation, ArmorStand.class);
                        Tower tower = new Tower(armorStand, 5.0, 1L, 10.0, (JavaPlugin) Tower_Defense.getPlugin(Tower_Defense.class));
                        towers.add(tower);

                        // SQLiteにタワーの情報を書き込む
                        towerManager.WriteTowerDatabase(TowerID, "Archer", 3, 1);
                        player.sendMessage("Count: " + TowerID);
                        player.sendMessage("Tower size: " + construction.GetSizeStructure("test_tower").x + " " + construction.GetSizeStructure("test_tower").y + " " + construction.GetSizeStructure("test_tower").z);

                        player.sendMessage("Tower constructed");
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