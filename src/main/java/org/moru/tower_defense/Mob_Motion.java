package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Mob_Motion extends BukkitRunnable {
    private final LivingEntity mob;

    public Mob_Motion(LivingEntity mob) {
        //mobを取得
        this.mob = mob;
        //mobのAIを無効化
        this.mob.setAI(false);
    }

    @Override
    public void run() {
        mob_motion();
    }

    private void mob_motion() {
        Location loc = mob.getLocation();

        Material WestDirectionBlock = Material.EMERALD_BLOCK;
        Material EastDirectionBlock = Material.GOLD_BLOCK;
        Material SouthDirectionBlock = Material.IRON_BLOCK;
        Material NorthDirectionBlock = Material.DIAMOND_BLOCK;
        Material MoveBlock = Material.LAPIS_BLOCK;

        for (int y = -2; y > -10; y--) {
            Material block = loc.getBlock().getRelative(0, y, 0).getType();
            if (block == MoveBlock) {
                //秒速0.5で移動
                Vector direction = mob.getLocation().getDirection();
                Location newLocation = mob.getLocation().add(direction.multiply(0.1));

                //新しい場所がブロックの中にあるかどうかを確認
                if (!newLocation.getBlock().isEmpty()) {
                    //新しい場所が空でない場合、移動をキャンセル
                    return;
                }
                mob.teleport(newLocation);
            }
        }
        Material under_block = loc.getBlock().getRelative(0, -2, 0).getType();
        if (under_block == WestDirectionBlock) {
            mob.setRotation(-90, mob.getLocation().getPitch());
        }
        if (under_block == EastDirectionBlock) {
            mob.setRotation(90, mob.getLocation().getPitch());
        }
        if (under_block == SouthDirectionBlock) {
            mob.setRotation(180, mob.getLocation().getPitch());
        }
        if (under_block == NorthDirectionBlock) {
            mob.setRotation(0, mob.getLocation().getPitch());
        }
    }
}