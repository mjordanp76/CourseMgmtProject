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

    public Account getUser(String usn) {
        String DbQuery = "SELECT * FROM Account WHERE usn = ?";

        try (PreparedStatement stmt = conn.prepareStatement(DbQuery)) {
            stmt.setString(1, usn);
            ResultSet rs = stmt.executeQuery();

            // debugging output
            if (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("accountID"));
                account.setUsn(rs.getString("usn"));
                account.setFirstName(rs.getString("fname"));
                account.setLastName(rs.getString("lname"));
                account.setDept(rs.getString("dept"));
                account.setRole(rs.getString("role"));
                account.setPwdHash(rs.getString("pswd"));

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

    public void saveLogin(String usn) {
        String findAccountIdQuery = "SELECT accountID FROM Account WHERE usn = ?";
        String insertSessionQuery = "INSERT INTO Session (accountID, event, time) VALUES (?, ?, datetime('now'))";

        try (PreparedStatement findStmt = conn.prepareStatement(findAccountIdQuery)) {
            findStmt.setString(1, usn);
            ResultSet rs = findStmt.executeQuery();

            if (rs.next()) {
                int accountID = rs.getInt("accountID");

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSessionQuery)) {
                    insertStmt.setInt(1, accountID);
                    insertStmt.setString(2, "login");
                    insertStmt.executeUpdate();
                }
            } else {
                System.err.println("saveLogin: Username not found: " + usn);
            }
        } catch (SQLException e) {
            System.err.println("saveLogin failed: " + e.getMessage());
        }
    }

    // public List<Course> getCourses(String usn) {

    // }

    // public List<Section> getSections(String usn) {

    // }

    // public List<Grade> getGrades(int courseID) {

    // }
}
