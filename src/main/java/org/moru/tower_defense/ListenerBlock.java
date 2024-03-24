package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;


public class ListenerBlock implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
                event.getHand() == EquipmentSlot.HAND &&
                !player.isSneaking()) {
            // プラットフォームかどうかを判定
            PlatformClick(event.getClickedBlock().getType(), event);
            // タワーかどうかを判定
            TowerClick(event);
        }
    }

    private void PlatformClick(Material clickedBlock, PlayerInteractEvent event) {
        ManagerPlatform managerPlatform = ManagerPlatform.getInstance();
        if (clickedBlock == Material.CHERRY_PLANKS) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                Location edgeLocation = managerPlatform.Platform(event.getClickedBlock().getLocation(), 3, 3, Material.CHERRY_PLANKS, event);
                if (edgeLocation != null) {
                    Player player = event.getPlayer();
                    Inventory gui = InventoryGUI.PlatformGUI();
                    player.openInventory(gui);
                }
            }
        }
    }

    private void TowerClick(PlayerInteractEvent event) {
        Location clickedBlockLocation = event.getClickedBlock().getLocation();
        SQLiteManagerTower sqliteManagerTower = SQLiteManagerTower.getInstance();
        int TowerID = sqliteManagerTower.GetTowerID(clickedBlockLocation);
        if (TowerID != 0) {
            Player player = (Player) event.getPlayer();
            player.sendMessage("TowerID " + TowerID + " がクリックされました!");
            Inventory gui = InventoryGUI.TowerGUI(TowerID);
            player.openInventory(gui);
        }
    }
}