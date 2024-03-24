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

public class ListenerInventoryClick implements Listener {
    private List<Tower> towers = new ArrayList<>();
    private int TowerID;
    private SQLiteManagerTower towerManager;

    public ListenerInventoryClick() {
        towerManager = SQLiteManagerTower.getInstance();
        TowerID = towerManager.GetLastTowerID() + 1;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getViewers().contains(event.getWhoClicked())) {
            if (event.getView().getTitle().equals("PlatformGUI")) {
                // GUI内のアイテムを取った時そのイベントをキャンセル
                event.setCancelled(true);
                ManagerPlatform managerPlatform = ManagerPlatform.getInstance();
                Player player = (Player) event.getWhoClicked();
                //send message
                Location Edgelocation = managerPlatform.getEdgelocation();
                player.sendMessage("You clicked at slot " + event.getSlot());
                player.sendMessage("EdgeLocation: " + Edgelocation);
                switch (event.getSlot()) {
                    case 2: // Oak_Planks
                        // タワーの建設
                        String StructureName = "archer_1";

                        Construction construction = new Construction();
                        construction.SummonStructure(Edgelocation, StructureName);
                        Construction.Size size = construction.GetSizeStructure(StructureName);// タワーのサイズを取得

                        //　攻撃用タワー(ArmorStand)の設定
                        Location spawnLocation = new Location(Edgelocation.getWorld(), Edgelocation.getX(), Edgelocation.getY(), Edgelocation.getZ());
                        spawnLocation.setX(spawnLocation.getX() + (double) (size.x / 2) - 0.5);
                        spawnLocation.setY(spawnLocation.getY() + size.y + 1.0);
                        spawnLocation.setZ(spawnLocation.getZ() + (double) (size.z / 2) - 0.5);
                        ArmorStand armorStand = Edgelocation.getWorld().spawn(spawnLocation, ArmorStand.class);
                        Tower tower = new Tower(armorStand, 100, 1L, 100.0, (JavaPlugin) Tower_Defense.getPlugin(Tower_Defense.class));
                        towers.add(tower);

                        // SQLiteにタワーの情報を書き込む
                        towerManager.WriteTowerDatabase(TowerID, "Archer", 3, 1);
                        player.sendMessage("Count: " + TowerID);
                        player.sendMessage("Tower size: " + construction.GetSizeStructure(StructureName).x + " " + construction.GetSizeStructure(StructureName).y + " " + construction.GetSizeStructure(StructureName).z);

                        // SQLiteにタワーの座標(Coordinates)を書き込む
                        towerManager.WriteCoordinates(TowerID, Edgelocation, size);

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
            } else if (event.getView().getTitle().equals("TowerGUI")) {
                // GUI内のアイテムを取った時そのイベントをキャンセル
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                player.sendMessage("You clicked at slot " + event.getSlot());

                switch (event.getSlot()) {
                    case 11: // RedGlassPanel
                        // Code to execute when RedGlassPanel is clicked
                        break;
                    case 12: // GreenGlassPanel
                        // Code to execute when GreenGlassPanel is clicked
                        break;
                    default:
                        break;
                }
            }
        }
    }
}