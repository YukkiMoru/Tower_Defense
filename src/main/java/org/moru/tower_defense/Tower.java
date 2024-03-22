package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class Tower {
    private ArmorStand armorStand;
    private double damage;
    private long fireRate;
    private double range;
    private JavaPlugin plugin;

    public Tower(ArmorStand armorStand, double damage, long fireRate, double range, JavaPlugin plugin) {
        this.armorStand = armorStand;
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;
        this.plugin = plugin;

        // Schedule the attackMobs method to be called periodically
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                attackMobs();
            }
        }, 0L, fireRate); // 20 ticks = 1 second

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
}