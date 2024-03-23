package org.moru.tower_defense;

import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteManagerTower {
    private static SQLiteManagerTower instance;
    private final SQLite sqlite;

    public SQLiteManagerTower() {　// Tower_Defense起動時
        sqlite = new SQLite();
//        sqlite.DeleteAllData(); // テーブルのデータを全て削除
        sqlite.createTableIfNotExists();
    }


    public static SQLiteManagerTower getInstance() {
        if (instance == null) {
            instance = new SQLiteManagerTower();
        }
        return instance;
    }

    /*
     * タワーのデータを保存する
     * @param TowerID
     * @param TowerName
     * @param TowerType
     * @param level
     */

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

    public void WriteTowerDatabase(int TowerID, String TowerName, int TowerType, int level) {
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
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            sqlite.disconnect();
        }
    }

    public TowerData GetTowerDatabase(int TowerID) {
        sqlite.connect();
        Connection conn = sqlite.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TowerData towerData = null;
        String sql = "SELECT * FROM tower_data WHERE TowerID = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, TowerID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String TowerName = rs.getString("TowerName");
                int TowerType = rs.getInt("TowerType");
                int level = rs.getInt("level");
                towerData = new TowerData(TowerID, TowerName, TowerType, level);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            sqlite.disconnect();
        }
        return towerData;
    }

    /*
     * タワーの座標を保存する
     * @param TowerID
     * @param coordinates
     */

    public static class Coordinates {
        private int TowerID;
        private int x;
        private int y;
        private int z;

        public Coordinates(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getTowerID() {
            return TowerID;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    public void WriteCoordinates(int TowerID, Location EdgeLocation, Construction.Size size) {
        sqlite.connect();
        Connection conn = sqlite.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO tower_coordinates(TowerID, x, y, z) VALUES(?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            for (int x = (int) EdgeLocation.getX(); x < EdgeLocation.getX() + size.x; x++) {
                for (int y = (int) EdgeLocation.getY(); y < (int) EdgeLocation.getY() + size.y; y++) {
                    for (int z = (int) EdgeLocation.getZ(); z < (int) EdgeLocation.getZ() + size.z; z++) {
                        pstmt.setInt(1, TowerID);
                        pstmt.setInt(2, x - 1);
                        pstmt.setInt(3, y + 1);
                        pstmt.setInt(4, z - 1);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            sqlite.disconnect();
        }
    }

//    public Coordinates GetCoordinates(int TowerID) {
//        sqlite.connect();
//        Connection conn = sqlite.getConnection();
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Coordinates coordinates = null;
//        String sql = "SELECT * FROM tower_coordinates WHERE TowerID = ?";
//        try {
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, TowerID);
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                int x = rs.getInt("x");
//                int y = rs.getInt("y");
//                int z = rs.getInt("z");
//                coordinates = new Coordinates(x, y, z);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            sqlite.disconnect();
//        }
//        return coordinates;
//    }

    public int GetTowerID(Location location) {
        sqlite.connect();
        Connection conn = sqlite.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int TowerID = 0;
        String sql = "SELECT TowerID FROM tower_coordinates WHERE x = ? AND y = ? AND z = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (int) location.getX());
            pstmt.setInt(2, (int) location.getY());
            pstmt.setInt(3, (int) location.getZ());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                TowerID = rs.getInt("TowerID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            sqlite.disconnect();
        }
        return TowerID;
    }

    public int GetLastTowerID() {
        sqlite.connect();
        Connection conn = sqlite.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int lastTowerID = 0;
        String sql = "SELECT MAX(TowerID) AS LastTowerID FROM tower_data";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                lastTowerID = rs.getInt("LastTowerID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            sqlite.disconnect();
        }
        return lastTowerID;
    }
}