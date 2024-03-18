package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI_Manager {
    private static final Logger LOGGER = Bukkit.getLogger();

    public void createAndSaveGui(String name, int size, String title) {
        // Create a new GUI
        Inventory gui = Bukkit.createInventory(null, size, title);

        // Save it to a YAML file
        Yaml yaml = new Yaml();
        try (FileWriter writer = new FileWriter("plugins/Tower_Defense/" + name + ".yml")) {
            yaml.dump(new GuiData(name, size, title), writer);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save GUI: " + name, e);
        }
    }

    public void showGui(String name, Player player) {
        // Load the GUI from the YAML file
        Yaml yaml = new Yaml();
        try (FileInputStream stream = new FileInputStream("plugins/Tower_Defense/" + name + ".yml")) {
            GuiData guiData = yaml.load(stream);

            // Create a new GUI from the loaded data
            Inventory gui = Bukkit.createInventory(null, guiData.getSize(), guiData.getTitle());

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

    private static class GuiData {
        private final String name;
        private final int size;
        private final String title;

        public GuiData(String name, int size, String title) {
            this.name = name;
            this.size = size;
            this.title = title;
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
    }
}