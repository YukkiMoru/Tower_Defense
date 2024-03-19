package org.moru.tower_defense;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryCloseListener implements Listener {
    private static final Logger LOGGER = Bukkit.getLogger();
    private GUI_Manager guiManager;

    public InventoryCloseListener(GUI_Manager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        LOGGER.log(Level.INFO, "onInventoryClose called");
        Inventory closedInventory = event.getInventory();
        if (closedInventory.equals(guiManager.getGui())) { // Check if the closed inventory is the GUI we created
            Yaml yaml = new Yaml();
            GUI_Manager.GuiData guiData = guiManager.createGuiData(closedInventory.getSize(), Arrays.asList(closedInventory.getContents()));
            try (FileWriter writer = new FileWriter("plugins/Tower_Defense/guis/" + guiData.getName() + ".yml")) {
                yaml.dump(guiData, writer);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to save GUI: " + guiData.getName(), e);
                LOGGER.log(Level.SEVERE, "File path: " + "plugins/Tower_Defense/guis/" + guiData.getName() + ".yml");
                File file = new File("plugins/Tower_Defense/guis/" + guiData.getName() + ".yml");
                LOGGER.log(Level.SEVERE, "File exists: " + file.exists());
                LOGGER.log(Level.SEVERE, "Write permission: " + file.canWrite());
            }
        }
    }
}