package org.moru.tower_defense;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Platform_Manager {

    private static Platform_Manager instance = null;
    private boolean debugMode = false; // デバッグモードのフラグ

    private Platform_Manager() {
    }

    public static Platform_Manager getInstance() {
        if (instance == null) {
            instance = new Platform_Manager();
        }
        return instance;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
    public boolean Platform(Location location, int sizeX, int sizeZ, Material material, PlayerInteractEvent event) {
        //クリックされた場所の座標
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();

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
        int PlatformX = east - west + 1;
        int PlatformY = south - north + 1;

        //Edge座標
        int EdgeX = x + west;
        int EdgeZ = z + north;

        if (debugMode) {
            Player player = event.getPlayer();
            player.sendMessage("West: " + -west);
            player.sendMessage("East: " + east);
            player.sendMessage("South: " + south);
            player.sendMessage("North: " + -north);
            player.sendMessage("Platform_Size_X: " + PlatformX);
            player.sendMessage("Platform_Size_Y: " + PlatformY);
            player.sendMessage("EdgeX: " + EdgeX + ", EdgeZ: " + EdgeZ);
            player.sendMessage("Clicked " + (location.getX()) + " " + (location.getY()) + " " + (location.getZ()));
        }

//        player.sendMessage("Center " + centerX + " " + (location.getY()) + " " + centerZ);
//        boolean IsPlatformFilled = true;
//        for (int i = 0; i <= north + south; i++) {
//            for (int j = 0; j <= west + east; j++) {
//                if (location.getWorld().getBlockAt(new Location(location.getWorld(), x + j, location.getY(), z + i)).getType() != material) {
//                    IsPlatformFilled = false;
//                }
//            }
//        }
//        if (IsPlatformFilled) {
//            player.sendMessage("You clicked part of " + distanceX + " * " + distanceY + " " + material + "!");
//        } else {
//            player.sendMessage("This Platform is not filled with " + material + "!");
//        }
//
//
//        //プラットフォームの判定
//        if (distanceX == sizeX && distanceY == sizeZ) {
//            for (int i = 0; i <= north + south; i++) {
//                for (int j = 0; j <= west + east; j++) {
//                    if (location.getWorld().getBlockAt(new Location(location.getWorld(), x + j, location.getY(), z + i)).getType() != material) {
//                        return false;
//                    }
//                }
//            }
//        } else {
//            return false;
//        }
        return false;
    }
}
