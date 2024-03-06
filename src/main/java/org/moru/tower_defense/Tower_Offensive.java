package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

import java.util.List;

public class Tower_Offensive {
    private ArmorStand armorStand;
    private double damage;
    private long fireRate;
    private double range;

    public Tower_Offensive(ArmorStand armorStand, double damage, long fireRate, double range) {
        this.armorStand = armorStand;
        this.damage = damage;
        this.fireRate = fireRate;
        this.range = range;
    }

    public void attackMobs() {
        List<Mob> mobs = armorStand.getWorld().getEntitiesByClass(Mob.class);
        for (Mob mob : mobs) {
            if (mob.getLocation().distance(armorStand.getLocation()) <= range) {
                shootArrow(mob);
            }
        }
    }

    private void shootArrow(LivingEntity target) {
        Location loc = armorStand.getEyeLocation().clone();
        Vector direction = target.getLocation().clone().subtract(loc).toVector();
        Arrow arrow = armorStand.getWorld().spawnArrow(loc, direction, (float) damage, 0);
        arrow.setShooter(armorStand);
    }
}