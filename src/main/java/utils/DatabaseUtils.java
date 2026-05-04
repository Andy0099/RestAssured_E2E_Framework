package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    
    private static Connection conn;

    public static void connectToDB() {
        String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : ConfigReader.getProperty("db_url");
        String user = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : ConfigReader.getProperty("db_username");
        String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : ConfigReader.getProperty("db_password");

        int attempts = 0;
        while (attempts < 15) {
            try {
                conn = DriverManager.getConnection(url, user, password);
                if (conn != null) {
                    System.out.println("Database Connection Successful to: " + url);
                    return; 
                }
            } catch (SQLException e) {
                attempts++;
                System.out.println("Attempt " + attempts + "/15: Waiting for DB... " + e.getMessage());
                try { Thread.sleep(5000); } catch (InterruptedException ie) {}
            }
        }
        throw new RuntimeException("Could not connect to Database after 15 attempts.");
    }

    // NEW MASTERY METHOD: To create tables and insert data
    public static void executeUpdate(String sql) {
        if (conn == null) connectToDB();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQL Update Error: " + e.getMessage());
        }
    }

    public static String getQueryResult(String query) {
        if (conn == null) connectToDB();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getString(1); 
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return null;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database Connection Closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
