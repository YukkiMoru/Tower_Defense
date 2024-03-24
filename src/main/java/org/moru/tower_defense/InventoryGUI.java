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

import javax.security.auth.login.CredentialNotFoundException;
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

        //create gui using sql

        gui.setItem(11, RedGlassPanel);
        gui.setItem(12, GreenGlassPanel);

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