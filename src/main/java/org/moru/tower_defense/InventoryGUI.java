package org.moru.tower_defense;
/*
このファイルは、Tower_DefenseプラグインのインベントリGUIクラスです。
InventoryGUIでGUIを作成し、ListenerInventoryClickでイベントを処理します。
*/
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        Config configManager = new Config(JavaPlugin.getPlugin(Tower_Defense.class));
        FileConfiguration config = configManager.loadConfig("archer");

        SQLManagerTower sqliteManagerTower = SQLManagerTower.getInstance();
        SQLManagerTower.TowerData TowerData = sqliteManagerTower.GetTowerDatabase(TowerID);

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

        gui.setItem(49, GoldIngot);
        gui.setItem(8, Barrier);

        if ("Archer".equals(TowerData.getTowerName())) {
            List<Map<?, ?>> paths = config.getMapList("archer");

            for (Map<?, ?> path : paths) {
                List<Map<String, Object>> levels = (List<Map<String, Object>>) path.get("levels");

                for (Map<String, Object> level : levels) {
                    if ((int) level.get("level") == TowerData.getLevel()) {
                        ItemStack Archer = new ItemStack(Material.BOW, 1);
                        ItemMeta meta = Archer.getItemMeta();

                        meta.setDisplayName(ChatColor.WHITE + "Archer Level " + level.get("level"));
                        meta.setLore(Arrays.asList(
                                ChatColor.WHITE + "Damage: " + level.get("damage"),
                                ChatColor.WHITE + "Attack Speed: " + level.get("attackspeed"),
                                ChatColor.WHITE + "Range: " + level.get("range"),
                                ChatColor.WHITE + "Skills: " + level.get("skills")
                        ));

                        Archer.setItemMeta(meta);
                        gui.setItem(4, Archer);
                    }
                }
            }
            int[] slots = {27, 28, 29, 30, 31, 32, 33, 34, 35};
            for (int i = 0; i < TowerData.getLevel(); i++) {
                gui.setItem(slots[i], GreenGlassPanel);
            }
            for (int i = TowerData.getLevel(); i < slots.length; i++) {
                gui.setItem(slots[i], RedGlassPanel);
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