package org.moru.tower_defense;
//ここでは主にmobの動きに関する処理を行います。

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Map;

/*
方向を決める
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
        // mobの位置から1マス下と2マス下のブロックの種類を取得
        Material block1 = loc.getBlock().getRelative(0, -1, 0).getType();
        Material block2 = loc.getBlock().getRelative(0, -2, 0).getType();

        // 各方向のブロックと対応するローテーション値をマップに格納
        Map<Material, Float> directionMap = new HashMap<>();
        directionMap.put(Material.valueOf("EMERALD_BLOCK"), -90f); //東
        directionMap.put(Material.valueOf("GOLD_BLOCK"), 90f); //西
        directionMap.put(Material.valueOf("IRON_BLOCK"), 0f); //南
        directionMap.put(Material.valueOf("DIAMOND_BLOCK"), 180f); //北

        // block1とblock2のどちらかが指定のブロックであれば、モブの向きを変える
        Float rotation = directionMap.get(block1);
        if (rotation == null) {
            rotation = directionMap.get(block2);
        }
        if (rotation != null) {
            mob.setRotation(rotation, mob.getLocation().getPitch());
        }
    }
}