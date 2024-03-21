package org.moru.tower_defense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLite {
    private Connection connection;

    public void createTableIfNotExists() {
        connect();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;

        String sql = "CREATE TABLE IF NOT EXISTS tower_data(TowerID INTEGER, TowerName TEXT, TowerType INTEGER, level INTEGER)";

        try {
            pstmt = conn.prepareStatement(sql);
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

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:TdSql.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}