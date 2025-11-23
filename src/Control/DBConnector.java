package Control;

import Entity.Account;
import Entity.Course;
import Entity.Section;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class DBConnector {
    
    private Connection conn;
    private int currentAccountID = -1;
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

                // save accountID 
                currentAccountID = account.getAccountID();

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

    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        System.out.println(currentAccountID); // debugging

        // debugging
        if (currentAccountID == -1) {
            System.err.println("getCourses method called before user login!");
            return courses;
        }

        String sql = "SELECT c.courseID, c.courseNum, c.courseName FROM Course c WHERE NOT EXISTS (SELECT 1 FROM Section s JOIN Enrollment e ON s.sectionID = e.sectionID WHERE s.courseID = c.courseID AND e.accountID = ?) AND (c.prereq IS NULL OR EXISTS (SELECT 1 FROM Section s2 JOIN Enrollment e2 ON s2.sectionID = e2.sectionID WHERE s2.courseID = c.prereq AND e2.accountID = ?))";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, currentAccountID);
            stmt.setInt(2, currentAccountID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int courseID = rs.getInt("courseID");
                int courseNum = rs.getInt("courseNum");
                String courseName = rs.getString("courseName");
                Course c = new Course();
                c.setCourseID(courseID);
                c.setCourseNum(courseNum);
                c.setCourseName(courseName);

                courses.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("DEBUG: getCourses returned " + courses.size() + " courses:");
    for (Course c : courses) {
        System.out.println("  ID: " + c.getCourseID() + ", Number: " + c.getCourseNum() + ", Name: " + c.getCourseName());
    }
        return courses;
    }

    public List<Section> getenrolledSections() {
        List<Section> enrolledSections = new ArrayList<>();
        String sql = "SELECT s.sectionID, s.courseID, c.courseNum, c.courseName, s.time, s.location " +
                     "FROM Section s " +
                     "JOIN Enrollment e ON s.sectionID = e.sectionID " +
                     "JOIN Course c ON s.courseID = c.courseID " +
                     "WHERE e.accountID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, currentAccountID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int sectionID = rs.getInt("sectionID");
                int courseID = rs.getInt("courseID");
                int courseNum = rs.getInt("courseNum");
                String courseName = rs.getString("courseName");
                String time = rs.getString("time");
                String location = rs.getString("location");
                Section s = new Section();
                s.setSectionID(sectionID);
                s.setCourseID(courseID);
                s.setCourseNum(courseNum);
                s.setCourseName(courseName);
                s.setTime(time);
                s.setLocation(location);
                enrolledSections.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrolledSections;
    }

    public List<Section> getSections(int courseID) {
        List<Section> sections = new ArrayList<>();
        String sql = "SELECT sectionID, sectionLetter, time, location " +
                    "FROM Section " +
                    "WHERE courseID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int sectionID = rs.getInt("sectionID");
                String letter = rs.getString("sectionLetter");
                String time = rs.getString("time");
                String location = rs.getString("location");
                Section s = new Section();
                s.setSectionID(sectionID);
                s.setSectionLetter(letter);
                s.setTime(time);
                s.setLocation(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    // public List<Grade> getGrades(int courseID) {

    // }

    public void registerStudentForSection(int accountID, int sectionID) {
        String sql = "INSERT INTO Enrollment (accountID, sectionID VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            stmt.setInt(2, sectionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
