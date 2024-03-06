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

public class Building implements Listener {
    private JavaPlugin plugin;
    private boolean cooldown = false;

    private Tower_Offensive tower;

    public Building(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !cooldown) {
            if (event.getClickedBlock().getType() == Material.OAK_PLANKS) {
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                    if (isCenterOf3x3(event.getClickedBlock().getLocation())) {
                        Player player = event.getPlayer();
                        player.sendMessage("You clicked the center of a 3x3 oak wood!3");

                        // Run code to summon the structure
                        summonStructure(event.getClickedBlock().getLocation());

                        // Create and setup the tower
                        ArmorStand armorStand = (ArmorStand) event.getClickedBlock().getWorld().spawn(event.getClickedBlock().getLocation().add(0, 1, 0), ArmorStand.class);
                        tower = new Tower_Offensive(armorStand, 5.0, 20L, 10.0);

                        // Apply cooldown
                        setCooldown();

                        // Cancel the event to prevent it from triggering again before the cooldown is applied
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

private boolean isCenterOf3x3(Location location) {
        // Check if the blocks around the clicked block form a 3x3x1 of oak wood
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (location.clone().add(x, 0, z).getBlock().getType() != Material.OAK_PLANKS) {
                    return false;
                }
            }
        }
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