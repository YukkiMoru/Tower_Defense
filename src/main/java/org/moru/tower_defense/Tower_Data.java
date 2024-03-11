package org.moru.tower_defense;

import org.bukkit.Location;

public class Tower_Data {
    private int size;
    private String type;
    private Location location;
    private int level;

    public Tower_Data(int size, String type, Location location, int level) {
        this.size = size;
        this.type = type;
        this.location = location;
        this.level = level;
    }

}
