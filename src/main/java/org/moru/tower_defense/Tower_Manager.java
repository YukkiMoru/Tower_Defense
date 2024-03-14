package org.moru.tower_defense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Tower_Manager {

    private HashMap<String, TowerData> towerDataMap = new HashMap<>();
    private SQLite sqlite;

    public Tower_Manager() {
        sqlite = new SQLite();
        // add default tower data
        addTowerData("tower", "0,0,0", "3", 1);
    }

    public static class TowerData {
        private String location;
        private String type;
        private int level;

        public TowerData(String location, String type, int level) {
            this.location = location;
            this.type = type;
            this.level = level;
        }

        public String getLocation() {
            return location;
        }

        public String getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }
    }

    public void addTowerData(String towerName, String location, String type, int level) {
        TowerData towerData = new TowerData(location, type, level);
        towerDataMap.put(towerName, towerData);
        writeToDatabase(towerName, towerData);
    }

    public TowerData getTowerData(String towerName) {
        return towerDataMap.get(towerName);
    }

    private void writeToDatabase(String towerName, TowerData towerData) {
        sqlite.connect();
        Connection conn = sqlite.getConnection();
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO tower_data(tower_name, location, type, level) VALUES(?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, towerName);
            pstmt.setString(2, towerData.location);
            pstmt.setString(3, towerData.type);
            pstmt.setInt(4, towerData.level);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            sqlite.disconnect();
        }
    }
}