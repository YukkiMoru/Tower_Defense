package org.moru.tower_defense;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;

public class displayInstances {
    public void displayInstances(Player player, Tower_Defense towerDefense) {
        Field[] fields = Tower_Defense.class.getDeclaredFields();
        player.sendMessage("Running instances:");
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            try {
                Object instance = field.get(towerDefense);
                if (instance != null) {
                    player.sendMessage(field.getName() + ": " + instance.toString());
                }
            } catch (IllegalAccessException e) {
                player.sendMessage("Error accessing field: " + field.getName());
            }
        });
    }
}