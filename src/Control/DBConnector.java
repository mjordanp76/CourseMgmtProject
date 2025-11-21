package Control;

import Entity.Account;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConnector {
    
    private Connection conn;
    private static final String DB_URL = "jdbc:sqlite:CourseMgmtDB.db";

    public DBConnector() {
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

    // Provides access to the database connection for other classes
    public Connection getConnection() {
        return conn;
    }

    // debugging
    public Account getUser(String usn) {
        String DbQuery = "SELECT * FROM Account WHERE usn = ?";

        try (PreparedStatement stmt = conn.prepareStatement(DbQuery)) {
            stmt.setString(1, usn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("accountID"));
                account.setUsn(rs.getString("usn"));
                account.setFirstName(rs.getString("fname"));
                account.setLastName(rs.getString("lname"));
                account.setDept(rs.getString("dept"));
                account.setRole(rs.getString("role"));
                account.setPswdHash(rs.getString("pswd"));

                // debugging output
                System.out.println("Fetched from DB: " +
                    rs.getInt("accountID") + " | " +
                    rs.getString("usn") + " | " +
                    rs.getString("fname") + " | " +
                    rs.getString("lname") + " | " +
                    rs.getString("dept") + " | " +
                    rs.getString("role"));
                
                // debugging output
                System.out.println("Data in newly-created Account object: ");
                account.display();

                return account;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
