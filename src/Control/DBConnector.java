package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private Connection conn;
     private static final String DB_URL = "jdbc:sqlite:CourseMgmtDB.db";
    
    public DBConnector() {
    }

    public DBConnector(Connection conn) {
        this.conn = conn;
    }

    public void initializeDB() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite database!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            conn = null; // ensure it's null if connection fails
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
