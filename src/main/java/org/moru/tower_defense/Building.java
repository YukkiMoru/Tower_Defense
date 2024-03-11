package org.moru.tower_defense;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.bukkit.BukkitWorld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.ArmorStand;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Building implements Listener {
    private JavaPlugin plugin;
    private boolean cooldown = false;

    private List<Tower> towers = new ArrayList<>();

    public Building(JavaPlugin plugin) {
        this.plugin = plugin;

        // Schedule the attackMobs method to be called periodically
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for (Tower tower : towers) {
                    tower.attackMobs();
                }
            }
        }, 0L, 20L); // 20 ticks = 1 second
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !cooldown) {
            if (event.getClickedBlock().getType() == Material.OAK_PLANKS) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                    if (isPartOf3x3(event.getClickedBlock().getLocation(), event)) {
                        Player player = event.getPlayer();
                        player.sendMessage("You clicked the center of a 3x3 oak wood!3");

//                        // Run code to summon the structure
//                        summonStructure(event.getClickedBlock().getLocation());
//
//                        // Create and set up the tower
//                        Location spawnLocation = event.getClickedBlock().getLocation().clone();
//                        spawnLocation.setX(Math.floor(spawnLocation.getX()) + 0.5);
//                        spawnLocation.setY(Math.floor(spawnLocation.getY()) + 7);
//                        spawnLocation.setZ(Math.floor(spawnLocation.getZ()) + 0.5);
//                        ArmorStand armorStand = (ArmorStand) event.getClickedBlock().getWorld().spawn(spawnLocation, ArmorStand.class);
//                        Tower tower = new Tower(armorStand, 5.0, 1L, 10.0);
//                        towers.add(tower);
//
//                        // Apply cooldown
//                        setCooldown();
//
//                        // Cancel the event to prevent it from triggering again before the cooldown is applied
//                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isPartOf3x3(Location location, PlayerInteractEvent event) {
        //クリックされた場所の座標
        double x = location.getX();
        double z = location.getZ();

        //ブロックの種類
        Material material = Material.OAK_PLANKS;

        //東西南北
        int west = 0;
        int east = 0;
        int south = 0;
        int north = 0;

        //東西南北のブロックの数を数える
        while (
                location.getWorld().getBlockAt(new Location(location.getWorld(), x - 1 + west, location.getY(), z)).getType() == material) {
            west--;
        }
        while (
                location.getWorld().getBlockAt(new Location(location.getWorld(), x + 1 + east, location.getY(), z)).getType() == material) {
            east++;
        }
        while (
                location.getWorld().getBlockAt(new Location(location.getWorld(), x, location.getY(), z + 1 + south)).getType() == material) {
            south++;
        }
        while (
                location.getWorld().getBlockAt(new Location(location.getWorld(), x, location.getY(), z - 1 + north)).getType() == material) {
            north--;
        }


        Player player = event.getPlayer();
        player.sendMessage("West: " + -west);
        player.sendMessage("East: " + east);
        player.sendMessage("South: " + south);
        player.sendMessage("North: " + -north);

        int distanceX = east - west + 1;
        int distanceY = south - north + 1;
        player.sendMessage("DistanceX: " + distanceX);
        player.sendMessage("DistanceY: " + distanceY);

        double centerX = (east + west) / 2;
        double centerZ = (south + north) / 2;

        if (distanceX % 2 == 0) {
            centerX += 0.5;
        }
        if (distanceY % 2 == 0) {
            centerZ += 0.5;
        }

        player.sendMessage("Center " + centerX + " " + (location.getY() - 1) + " " + centerZ);


        for (int i = 0; i <= north + south; i++) {
            for (int j = 0; j <= west + east; j++) {
                if (location.getWorld().getBlockAt(new Location(location.getWorld(), x + j, location.getY(), z + i)).getType() != material) {
                    return false;
                }
            }
        }
        player.sendMessage("You clicked part of " + distanceX + " * " + distanceY + " " + material + "!");
        return true;
    }


    private void summonStructure(Location location) {
        File schematic = new File("plugins/WorldEdit/schematics/test_tower.schem");
        WorldEdit worldEdit = WorldEdit.getInstance();
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        try (Closer closer = Closer.create()) {
            FileInputStream fis = closer.register(new FileInputStream(schematic));
            BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
            ClipboardReader reader = closer.register(format.getReader(bis));

            Clipboard clipboard = reader.read();

            try (EditSession editSession = worldEdit.getEditSessionFactory().getEditSession(new BukkitWorld(location.getWorld()), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                        .ignoreAirBlocks(false)
                        .build();
                Operations.complete(operation);
            } catch (WorldEditException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCooldown() {
        cooldown = true;
        System.out.println("Cooldown started"); // Debug message

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            cooldown = false;
            System.out.println("Cooldown ended"); // Debug message
        }, 200L); // 2 ticks cooldown (20 ticks/sec * 2 sec = 40 ticks)
    }
}