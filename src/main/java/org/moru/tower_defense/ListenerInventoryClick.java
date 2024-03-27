package org.moru.tower_defense;

import org.bukkit.Bukkit;
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

    private ListenerBlock listenerBlock;

    public ListenerInventoryClick(ListenerBlock listenerBlock) {
        this.listenerBlock = listenerBlock;
        towerManager = SQLiteManagerTower.getInstance();
        TowerID = towerManager.GetLastTowerID() + 1;
    }

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

                        player.playSound(player.getLocation(), "minecraft:block.anvil.destroy", 1.0f, 0.5f);

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
                Construction construction = new Construction();
                switch (event.getSlot()) {
                    case 40: // Upgrade Tower
                        player.sendMessage("TowerID: " + listenerBlock.getCurrentTowerID());
                        int ClickedTowerID = listenerBlock.getCurrentTowerID();

                        towerManager.UpgradeTower(ClickedTowerID);
                        //reload gui
                        InventoryGUI.TowerGUI(ClickedTowerID);
                        player.playSound(player.getLocation(), "minecraft:block.anvil.destroy", 1.0f, 0.5f);
                        //タワーをアップグレード
                        SQLiteManagerTower sqliteManagerTower = SQLiteManagerTower.getInstance();
                        SQLiteManagerTower.TowerData TowerData = sqliteManagerTower.GetTowerDatabase(ClickedTowerID);
                        //新しいタワーを召喚
                        ManagerPlatform managerPlatform = ManagerPlatform.getInstance();
                        Location Edgelocation = managerPlatform.getEdgelocation();
                        String StructureName = "archer_" + TowerData.getLevel();
                        player.sendMessage("StructureName: " + StructureName);
                        construction.SummonStructure(Edgelocation, StructureName);
                        player.sendMessage("Tower upgraded");
                        break;

                    case 8: // Remove Tower
                        // Get the ID of the tower to be removed
                        int towerIdToRemove = listenerBlock.getCurrentTowerID();

                        // タワーの削除
                        construction.RemoveStructure(towerIdToRemove);
                        // データベース内のタワーの削除
                        towerManager.removeTower(towerIdToRemove);
                        towerManager.RemoveTowerCoordinates(towerIdToRemove);

                        player.sendMessage("Tower removed");
                        break;
                    default:
                        break;
                }
            }
        }
    }
}