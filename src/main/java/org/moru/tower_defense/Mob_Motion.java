package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class Mob_Motion extends BukkitRunnable {
    private final LivingEntity mob;

    public Mob_Motion(LivingEntity mob) {
        this.mob = mob;
    }

    @Override
    public void run() {
        mobMotion();
    }

    private void mobMotion() {
        Location loc = mob.getLocation();
        Material block = loc.getBlock().getRelative(0, -1, 0).getType();

        Material westDirectionBlock = Material.EMERALD_BLOCK;
        Material eastDirectionBlock = Material.GOLD_BLOCK;
        Material southDirectionBlock = Material.IRON_BLOCK;
        Material northDirectionBlock = Material.DIAMOND_BLOCK;
        Material moveBlock = Material.LAPIS_BLOCK;

        //ブロックに応じてmobの向きを変える
        if (block == westDirectionBlock) {
            mob.setRotation(-90, mob.getLocation().getPitch());
        }
        if (block == eastDirectionBlock) {
            mob.setRotation(90, mob.getLocation().getPitch());
        }
        if (block == southDirectionBlock) {
            mob.setRotation(180, mob.getLocation().getPitch());
        }
        if (block == northDirectionBlock) {
            mob.setRotation(0, mob.getLocation().getPitch());
        }
        //ブロックに応じてmobの動きを変える
        if (block == moveBlock) {
            mob.setVelocity(mob.getLocation().getDirection().multiply(0.5));
        }
    }
}