package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

import java.util.List;

public class Tower {
    private ArmorStand armorStand;
    private double damage;
    private long fireRate;
    private double range;


    public Tower(ArmorStand armorStand, double damage, long fireRate, double range) {
        this.armorStand = armorStand;
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;

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
    }
}