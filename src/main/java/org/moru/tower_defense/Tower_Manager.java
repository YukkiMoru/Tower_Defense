package org.moru.tower_defense;

import java.util.HashMap;


public class Tower_Manager {


    // データを保存するHashMap
    private HashMap<String, TowerData> towerDataMap = new HashMap<>();

    // タワーデータのクラス
    private class TowerData {
        String location;
        String type;
        int level;

        public TowerData(String location, String type, int level) {
            this.location = location;
            this.type = type;
            this.level = level;
        }
    }

    // タワーデータを追加するメソッド
    public void addTowerData(String towerName, String location, String type, int level) {
        TowerData towerData = new TowerData(location, type, level);
        towerDataMap.put(towerName, towerData);
    }

    // タワーデータを取得するメソッド
    public TowerData getTowerData(String towerName) {
        return towerDataMap.get(towerName);
    }
}