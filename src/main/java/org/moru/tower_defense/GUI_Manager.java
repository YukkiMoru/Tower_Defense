package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

public class GUI_Manager implements Listener {
    private static final Logger LOGGER = Bukkit.getLogger();
    private String title;
    private String name;
    private Inventory gui;
    private Map<Player, Inventory> playerGuis = new HashMap<>();

    public Inventory CreateGUI(Player player, String name, int size, String title) {
        this.title = title;
        this.name = name;
        this.gui = Bukkit.createInventory(null, size, title);
        playerGuis.put(player, this.gui);  // GUIをマップに追加
        Logger.getLogger("Minecraft").info("[Tower_Defense]: GUIの" + name + "が作成されました！");
        player.openInventory(this.gui);
        return this.gui;
    }

    public Inventory getGui(Player player) {
        return playerGuis.get(player);
    }

    public GuiData createGuiData(int size, List<ItemStack> items) {
        return new GuiData(this.name, size, this.title, items);
    }


    public void openGui(Player player, Inventory gui) {
        playerGuis.put(player, gui);
        player.openInventory(gui);
    }

    public void showGui(String name, Player player) {
        // Load the GUI from the YAML file
        Yaml yaml = new Yaml();
        try (FileInputStream stream = new FileInputStream("plugins/Tower_Defense/guis/" + name + ".yml")) {
            GuiData guiData = yaml.load(stream);

            // Create a new GUI from the loaded data
            Inventory gui = Bukkit.createInventory(null, guiData.getSize(), guiData.getTitle());

            // Set the contents of the GUI
            ItemStack[] items = new ItemStack[guiData.getItems().size()];
            gui.setContents(guiData.getItems().toArray(items));

            // Show it to the player
            player.openInventory(gui);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load GUI: " + name, e);
        }
    }

    public void removeGui(String name) {
        // Delete the YAML file
        Path path = Paths.get("plugins/Tower_Defense/guis/" + name + ".yml");
        try {
            Files.delete(path);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete GUI: " + name, e);
        }
    }

    public static class GuiData {
        private final String name;
        private final int size;
        private final String title;
        private final List<ItemStack> items;

        public GuiData(String name, int size, String title, List<ItemStack> items) {
            this.name = name;
            this.size = size;
            this.title = title;
            this.items = items;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }

        public String getTitle() {
            return title;
        }

        public List<ItemStack> getItems() {
            return items;
        }
    }

    // "plugins/Tower_Defense/guis/がない場合それを作る"
    public void CreateDirectory() {
        Path path = Paths.get("plugins/Tower_Defense/guis/");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to create directory: " + path, e);
            }
        }
    }
}