package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Platform_Manager {
    public boolean Platform(Location location, int sizeX, int sizeZ, Material material, PlayerInteractEvent event) {
        //クリックされた場所の座標
        double x = location.getX();
        double z = location.getZ();

        //東西南北
        int west = 0, east = 0, south = 0, north = 0;

        //東西南北のブロックの数を数える
        while (location.getWorld().getBlockAt((int) x - 1 + west, (int) location.getY(), (int) z).getType() == material)
            west--;
        while (location.getWorld().getBlockAt((int) x + 1 + east, (int) location.getY(), (int) z).getType() == material)
            east++;
        while (location.getWorld().getBlockAt((int) x, (int) location.getY(), (int) z + 1 + south).getType() == material)
            south++;
        while (location.getWorld().getBlockAt((int) x, (int) location.getY(), (int) z - 1 + north).getType() == material)
            north--;

        //東西南北のブロックの数を数える
        int distanceX = east - west + 1;
        int distanceY = south - north + 1;

        //中心座標
        double centerX = (east + west) / 2;
        double centerZ = (south + north) / 2;

        //デバッグゾーン
        if (distanceX % 2 == 0) {
            centerX += 0.5;
        }
        if (distanceY % 2 == 0) {
            centerZ += 0.5;
        }
        Player player = event.getPlayer();
        player.sendMessage("West: " + -west);
        player.sendMessage("East: " + east);
        player.sendMessage("South: " + south);
        player.sendMessage("North: " + -north);
        player.sendMessage("Platform_Size_X: " + distanceX);
        player.sendMessage("Platform_Size_Y: " + distanceY);
        if (distanceX % 2 == 0) {
            centerX += 0.5;
        }
        if (distanceY % 2 == 0) {
            centerZ += 0.5;
        }
        player.sendMessage("Center " + centerX + " " + (location.getY()) + " " + centerZ);
        player.sendMessage("You clicked part of " + distanceX + " * " + distanceY + " " + material + "!");

        //プラットフォームの判定
        if (distanceX == sizeX && distanceY == sizeZ) {
            for (int i = 0; i <= north + south; i++) {
                for (int j = 0; j <= west + east; j++) {
                    if (location.getWorld().getBlockAt(new Location(location.getWorld(), x + j, location.getY(), z + i)).getType() != material) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
