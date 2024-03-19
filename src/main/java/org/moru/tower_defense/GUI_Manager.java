package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI_Manager implements Listener {
    private static final Logger LOGGER = Bukkit.getLogger();
    private String title;
    private String name;
    private Inventory gui;

    public Inventory CreateGUI(Player player, String name, int size, String title) {
        this.title = title;
        this.name = name;
        this.gui = Bukkit.createInventory(null, size, title);
        player.openInventory(this.gui);

        return this.gui;
    }

    public Inventory getGui() {
        return this.gui;
    }

    public GuiData createGuiData(int size, List<ItemStack> items) {
        return new GuiData(this.name, size, this.title, items);
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
        Path path = Paths.get("plugins/Tower_Defense/" + name + ".yml");
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
}