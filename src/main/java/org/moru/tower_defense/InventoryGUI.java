package org.moru.tower_defense;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.ChatColor;

import java.util.UUID;

public class InventoryGUI {
    public static Inventory PlatformGUI() {
        Inventory gui = CreateInventory("PlatformGUI", 27);

        ItemStack Diamond = CreateItem(Material.DIAMOND, 1, "Click me!", ChatColor.AQUA);
        ItemStack Oak_Planks = CreateItem(Material.OAK_PLANKS, 1, "Archer Tower", ChatColor.GREEN);
        ItemStack Warden = CreatePlayerHead("Warden", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNmMzY3NGIyZGRjMGVmN2MzOWUzYjljNmI1ODY3N2RlNWNmMzc3ZDJlYjA3M2YyZjNmZTUwOTE5YjFjYTRjOSJ9fX0=", ChatColor.RED);

        // Add the items to the inventory
        gui.setItem(2, Oak_Planks);
        gui.setItem(25, Diamond);
        gui.setItem(26, Warden);  // Add the player head to the inventory

        return gui;
    }

    public static Inventory TowerGUI(int TowerID) {
        SQLiteManagerTower sqliteManagerTower = SQLiteManagerTower.getInstance();
        SQLiteManagerTower.TowerData TowerData = sqliteManagerTower.GetTowerDatabase(TowerID);
        //チャットにメッセージを送信

        Bukkit.broadcastMessage("TowerID: " + TowerData.getTowerID());
        Bukkit.broadcastMessage("TowerName: " + TowerData.getTowerName());
        Bukkit.broadcastMessage("TowerType: " + TowerData.getTowerType());
        Bukkit.broadcastMessage("Level: " + TowerData.getLevel());

        Inventory gui = CreateInventory("TowerGUI", 54);


        ItemStack RedGlassPanel = CreateItem(Material.RED_STAINED_GLASS_PANE, 1, "Red Glass Panel", ChatColor.RED);
        ItemStack GreenGlassPanel = CreateItem(Material.GREEN_STAINED_GLASS_PANE, 1, "Green Glass Panel", ChatColor.GREEN);
        ItemStack GrayGlassPanel = CreateItem(Material.GRAY_STAINED_GLASS_PANE, 1, "", null);
        ItemStack GoldIngot = CreateItem(Material.GOLD_INGOT, 1, "Upgrade", ChatColor.GOLD);
        ItemStack Barrier = CreateItem(Material.BARRIER, 1, "Remove", ChatColor.RED);

        gui.setItem(40, GoldIngot);
        gui.setItem(8, Barrier);


        if ("Archer".equals(TowerData.getTowerName())) {
            ItemStack Archer = CreateItem(Material.BOW, 1, "Archer", ChatColor.WHITE);
            gui.setItem(4, Archer);

            switch (TowerData.getLevel()) {
                case 1:
                    gui.setItem(11, GreenGlassPanel);
                    gui.setItem(12, RedGlassPanel);
                    gui.setItem(13, RedGlassPanel);
                    gui.setItem(14, RedGlassPanel);
                    gui.setItem(15, RedGlassPanel);
                    break;
                case 2:
                    gui.setItem(11, GreenGlassPanel);
                    gui.setItem(12, GreenGlassPanel);
                    gui.setItem(13, RedGlassPanel);
                    gui.setItem(14, RedGlassPanel);
                    gui.setItem(15, RedGlassPanel);
                    break;
                case 3:
                    gui.setItem(11, GreenGlassPanel);
                    gui.setItem(12, GreenGlassPanel);
                    gui.setItem(13, GreenGlassPanel);
                    gui.setItem(14, RedGlassPanel);
                    gui.setItem(15, RedGlassPanel);
                    break;
                case 4:
                    gui.setItem(11, GreenGlassPanel);
                    gui.setItem(12, GreenGlassPanel);
                    gui.setItem(13, GreenGlassPanel);
                    gui.setItem(14, GreenGlassPanel);
                    gui.setItem(15, RedGlassPanel);
                    break;
            }

        }


        return gui;
    }

    private static Inventory CreateInventory(String name, int size) {
        return Bukkit.createInventory(null, size, name);
    }

    private static ItemStack CreateItem(Material material, int amount, String displayName, ChatColor color) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color + displayName);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack CreatePlayerHead(String name, String value, ChatColor color) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        profile.setProperty(new ProfileProperty("textures", value));
        skullMeta.setPlayerProfile(profile);

        skullMeta.setDisplayName(color + name);
        playerHead.setItemMeta(skullMeta);

        return playerHead;
    }
}