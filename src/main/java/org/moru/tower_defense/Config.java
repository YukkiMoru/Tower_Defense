package org.moru.tower_defense;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Config {
    private JavaPlugin plugin;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        createDirectory();
        createConfig("original", "config");
        createConfig("archer", "archer");
    }

    private boolean getcheck(String chechedfile) {
        File checkedfile = new File(plugin.getDataFolder(), chechedfile);
        return checkedfile.exists();
    }

    public void createConfig(String OriginalName, String CopyName) {
        File configFile = new File(plugin.getDataFolder(), CopyName + ".yml");
        if (!configFile.exists()) {
            try (InputStream in = plugin.getResource(OriginalName + ".yml")) {
                if (in == null) {
                    throw new IOException("Resource original.yml not found");
                }
                Files.copy(in, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createDirectory() {
        Path path = Paths.get("plugins/Tower_Defense");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ShowConfig(CommandSender sender, String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");
        if (configFile.exists()) {
            try {
                Files.lines(configFile.toPath()).forEach(sender::sendMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration loadConfig(String configName) {
        File configFile = new File(plugin.getDataFolder(), configName + ".yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }
}