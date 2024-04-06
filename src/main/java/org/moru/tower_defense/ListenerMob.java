package org.moru.tower_defense;
/*
このクラスは、Tower_Defenseプラグインのモブリスナークラスです。
モブの動きを制御します。
 */
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerMob implements Listener {
    private final JavaPlugin plugin;

    public ListenerMob(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity mob = (LivingEntity) event.getEntity();
        MotionMob motionMob = new MotionMob(mob);
        motionMob.runTaskTimer(plugin, 0L, 1L);
    }
}