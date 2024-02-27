package org.moru.tower_defense;
//ここでは主にmobの動きに関する処理を行います。

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/*
方向を決める
　北
西  東　
  南
String使って、ブロックに応じてmobの向きを変える
その宣言する
東=Gold_Block
西=Emerald_Block
南=Iron_Block
北=Diamond_Block
 */


public class mob_motion extends BukkitRunnable {
    private final LivingEntity mob;

    public mob_motion(LivingEntity mob) {
        this.mob = mob;
    }

    @Override
    public void run() {
        Location loc = mob.getLocation();
        mob_motion(loc, mob);
    }

    public static void mob_motion(Location loc, LivingEntity mob) {
        Material block = loc.getBlock().getRelative(0, -1, 0).getType();

        String west_direction_block = "EMERALD_BLOCK";
        String east_direction_block = "GOLD_BLOCK";
        String south_direction_block = "IRON_BLOCK";
        String north_direction_block = "DIAMOND_BLOCK";

        if (block.toString().equals(west_direction_block)) {
            mob.setRotation(-90, mob.getLocation().getPitch());
        }
        if (block.toString().equals(east_direction_block)) {
            mob.setRotation(90, mob.getLocation().getPitch());
        }
        if (block.toString().equals(south_direction_block)) {
            mob.setRotation(180, mob.getLocation().getPitch());
        }
        if (block.toString().equals(north_direction_block)) {
            mob.setRotation(0, mob.getLocation().getPitch());
        }
    }
}