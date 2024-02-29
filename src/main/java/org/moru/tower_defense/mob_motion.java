package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class mob_motion extends BukkitRunnable {
    private final LivingEntity mob;

    public mob_motion(LivingEntity mob) {
        this.mob = mob;
    }

    @Override
    public void run() {
        Location loc = mob.getLocation();
        mob_motion(loc, mob);
        moveInDirection(mob);
    }

    public static void mob_motion(Location loc, LivingEntity mob) {
        Material block1 = loc.getBlock().getRelative(0, -1, 0).getType();
        Material block2 = loc.getBlock().getRelative(0, -2, 0).getType();

        Map<Material, Float> directionMap = new HashMap<>();
        directionMap.put(Material.valueOf("EMERALD_BLOCK"), -90f);
        directionMap.put(Material.valueOf("GOLD_BLOCK"), 90f);
        directionMap.put(Material.valueOf("IRON_BLOCK"), 0f);
        directionMap.put(Material.valueOf("DIAMOND_BLOCK"), 180f);

        Float rotation = directionMap.get(block1);
        if (rotation == null) {
            rotation = directionMap.get(block2);
        }
        if (rotation != null) {
            mob.setRotation(rotation, mob.getLocation().getPitch());
        } else {
            // 該当するブロックがない場合、モブは現在の向きを保持し、前進し続けます。
            moveInDirection(mob);
        }
    }

    public static void moveInDirection(LivingEntity mob) {
        Location loc = mob.getLocation();
        Vector direction = loc.getDirection().normalize();
        loc.add(direction);
    }
}