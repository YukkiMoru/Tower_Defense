package org.moru.tower_defense;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Config {
    private JavaPlugin plugin;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        if(!checkFile()){
            createConfig();
        }
    }

    public boolean checkFile() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        return configFile.exists();
    }

    public void createConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try (InputStream in = plugin.getResource("original-config.yml")) {
                if (in == null) {
                    throw new IOException("Resource original-config.yml not found");
                }
                Files.copy(in, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}