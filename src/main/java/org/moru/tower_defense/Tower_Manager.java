package org.moru.tower_defense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Tower_Manager {

    private HashMap<String, TowerData> towerDataMap = new HashMap<>();
    private SQLite sqlite;

    public Tower_Manager() {
        sqlite = new SQLite();
        // add default tower data
        addTowerData(1, "Archer", 3, 1);
    }

    public static class TowerData {

        private int TowerID;
        private String TowerName;
        private int TowerType;
        private int level;

        public TowerData(int TowerID, String TowerName, int TowerType, int level) {
            this.TowerID = TowerID;
            this.TowerName = TowerName;
            this.TowerType = TowerType;
            this.level = level;
        }

        public int getTowerID() {
            return TowerID;
        }

        public String getTowerName() {
            return TowerName;
        }

        public int getTowerType() {
            return TowerType;
        }

        public int getLevel() {
            return level;
        }
    }

    public void addTowerData(int TowerID, String TowerName, int TowerType, int level) {
        TowerData towerData = new TowerData(TowerID, TowerName, TowerType, level);
        towerDataMap.put(TowerName, towerData);
        writeToDatabase(TowerID, TowerName, TowerType, level);
    }

    public TowerData getTowerData(int TowerID) {
        return towerDataMap.get(TowerID);
    }

    private void writeToDatabase(int TowerID, String TowerName, int TowerType, int level) {
        sqlite.connect();
        Connection conn = sqlite.getConnection();
        PreparedStatement pstmt = null;

        String sql = "INSERT INTO tower_data(TowerID, TowerName, TowerType, level) VALUES(?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, TowerID);
            pstmt.setString(2, TowerName);
            pstmt.setInt(3, TowerType);
            pstmt.setInt(4, level);
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