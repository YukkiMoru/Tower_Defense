package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower {
    private static Map<Integer, Tower> towers = new HashMap<>(); // Add this line to keep track of all Tower instances

    private ArmorStand armorStand;
    private final double damage;
    private final long fireRate;
    private final double range;
    private final int TowerID;
    private final JavaPlugin plugin;
    private final BukkitTask attackTask;

    public Tower(ArmorStand armorStand, double damage, long fireRate, double range, int TowerID, JavaPlugin plugin) {
        this.armorStand = armorStand;
        this.armorStand.setCustomName(String.valueOf(TowerID));
        this.armorStand.setCustomNameVisible(true);
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;
        this.TowerID = TowerID;
        this.plugin = plugin;

        // Schedule the attackMobs method to be called periodically
        this.attackTask = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                attackMobs();
            }
        }, 0L, fireRate); // 20 ticks = 1 second

        towers.put(TowerID, this); // Add this line to add the new Tower instance to the map
    }

    public ArmorStand getArmorStand() {
        return this.armorStand;
    }

    public void updateArmorStand(ArmorStand newArmorStand) {
        this.armorStand.remove(); // remove the old ArmorStand
        this.armorStand = newArmorStand; // replace with the new ArmorStand
    }

    public void cancelAttackTask() {
        if (this.attackTask != null) {
            this.attackTask.cancel();
        }
    }

    public void attackMobs() {
        List<Entity> nearbyEntities = armorStand.getNearbyEntities(range, range, range);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof Monster) {
                shootArrow((LivingEntity) entity);
                break;
            }
        }
    }

    private void shootArrow(LivingEntity target) {
        Location loc = armorStand.getEyeLocation().clone();
        Location targetLocation = target.getLocation().clone();
        // Adjust the target location to aim for the head
        targetLocation.add(0, target.getHeight() * 0.9, 0);
        Vector direction = targetLocation.subtract(loc).toVector();
        Arrow arrow = armorStand.getWorld().spawnArrow(loc, direction, (float) damage, 0);
        arrow.setShooter(armorStand);
        arrow.setDamage(damage); // Add this line to set the damage of the arrow
    }

    public static Tower getTowerById(int TowerID) { // Add this method
        return towers.get(TowerID);
    }

    public static void removeTowerStand(int TowerID) {
        Tower tower = getTowerById(TowerID);
        if (tower != null) {
            for (Entity entity : tower.getArmorStand().getWorld().getEntities()) {
                if (entity instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (String.valueOf(TowerID).equals(armorStand.getCustomName())) {
                        armorStand.remove();
                        tower.cancelAttackTask();
                        towers.remove(TowerID); // Remove the tower from the map
                        break;
                    }
                }
            }
        }
    }
}