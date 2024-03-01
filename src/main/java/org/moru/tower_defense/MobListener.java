package org.moru.tower_defense;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MobListener implements Listener {
    private final JavaPlugin plugin;

    public MobListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity mob = (LivingEntity) event.getEntity();
            Mob_Motion mobMotion = new Mob_Motion(mob);
            mobMotion.runTaskTimer(plugin, 0L, 1L);
        }
    }
}