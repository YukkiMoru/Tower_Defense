package org.moru.tower_defense;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
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
import java.util.Map;

public class Config {
    private static Config instance;
    private JavaPlugin plugin;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        createDirectory();
        createConfig("original", "config");
        createConfig("archer", "archer");
    }

    public static Config getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new Config(plugin);
        }
        return instance;
    }

    public void createConfig(String OriginalName, String CopyName) {
        File configFile = new File(plugin.getDataFolder(), CopyName + ".yml");
        if (!configFile.exists()) {
            try (InputStream in = plugin.getResource(OriginalName + ".yml")) {
                if (in == null) {
                    throw new IOException("Could not find resource " + OriginalName + ".yml");
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

    public Map<String, Object> LoadConfig(String configname, int path, String level) {
        FileConfiguration config = loadConfig(configname);
        ConfigurationSection section = config.getConfigurationSection(configname + "." + path + ".levels");

        for (String key : section.getKeys(false)) {
            if (key.equals(level)) {
                return section.getConfigurationSection(key).getValues(true);
            }
        }

        return null;
    }
}