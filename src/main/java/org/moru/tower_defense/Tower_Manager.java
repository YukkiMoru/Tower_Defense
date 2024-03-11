package org.moru.tower_defense;

import java.util.HashMap;
import java.util.Map;

public class Tower_Manager {
    private Map<Integer, Tower_Data> towerDataMap;

    public Tower_Manager() {
        this.towerDataMap = new HashMap<>();
    }

    public Map<Integer, Tower_Data> getTowerDataMap() {
        return towerDataMap;
    }

    public void addTowerData(int id, Tower_Data towerData) {
        towerDataMap.put(id, towerData);
    }
}