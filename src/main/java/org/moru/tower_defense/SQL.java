package org.moru.tower_defense;
/*
このクラスは、Tower_DefenseプラグインのSQLiteクラスです。
SQLに関した処理をします。
SQLデータベースの作成、テーブルの作成、データの削除を行います。
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {
    private static Connection connection;

    public static void createTableIfNotExists() {
        connect();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;

        String sql1 = "CREATE TABLE IF NOT EXISTS tower_data(TowerID INTEGER, TowerName TEXT, TowerType INTEGER, level INTEGER)";
        String sql2 = "CREATE TABLE IF NOT EXISTS tower_coordinates(TowerID INTEGER, x INTEGER, y INTEGER, z INTEGER)";

        try {
            pstmt = conn.prepareStatement(sql1);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(sql2);
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
            disconnect();
        }
    }

    public static void DeleteAllData() {
        connect();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        String sql1 = "DELETE FROM tower_data";
        String sql2 = "DELETE FROM tower_coordinates";
        try {
            pstmt = conn.prepareStatement(sql1);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(sql2);
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
            disconnect();
        }
    }

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:TdSql.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}