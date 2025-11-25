package Control;

import Entity.Account;
import Entity.Course;
import Entity.GradeList;
import Entity.Section;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void saveLogout(int accountID, String event) {
        String insertLogoutQuery = "INSERT INTO Session (accountID, event, time) VALUES (?, ?, datetime('now'))";

        try (PreparedStatement saveStmt = conn.prepareStatement(insertLogoutQuery)) {
            saveStmt.setInt(1, accountID);
            saveStmt.setString(2, event);
            saveStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

        String sql = "SELECT c.courseID, c.courseNum, c.courseName, c.dept FROM Course c WHERE NOT EXISTS (SELECT 1 FROM Section s JOIN Enrollment e ON s.sectionID = e.sectionID WHERE s.courseID = c.courseID AND e.accountID = ?) AND (c.prereq IS NULL OR EXISTS (SELECT 1 FROM Section s2 JOIN Enrollment e2 ON s2.sectionID = e2.sectionID WHERE s2.courseID = c.prereq AND e2.accountID = ?))";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, currentAccountID);
            stmt.setInt(2, currentAccountID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int courseID = rs.getInt("courseID");
                int courseNum = rs.getInt("courseNum");
                String courseName = rs.getString("courseName");
                String dept = rs.getString("dept");
                Course c = new Course();
                c.setCourseID(courseID);
                c.setCourseNum(courseNum);
                c.setCourseName(courseName);
                c.setDept(dept);

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
        String sql = "SELECT s.sectionID, s.courseID, c.courseNum, c.courseName, c.dept, " +
                     "s.time, s.location " +
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
                String dept = rs.getString("dept");
                String time = rs.getString("time");
                String location = rs.getString("location");
                Section s = new Section();
                s.setSectionID(sectionID);
                s.setCourseID(courseID);
                s.setCourseNum(courseNum);
                s.setCourseName(courseName);
                s.setDept(dept);
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
        String sql = "SELECT s.sectionID, s.sectionLetter, s.time, s.location, c.dept, c.courseNum " +
                    "FROM Section s JOIN Course c ON s.courseID = c.courseID " +
                    "WHERE s.courseID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int sectionID = rs.getInt("sectionID");
                String letter = rs.getString("sectionLetter");
                String time = rs.getString("time");
                String location = rs.getString("location");
                String dept = rs.getString("dept");
                int courseNum = rs.getInt("courseNum");
                Section s = new Section();
                s.setSectionID(sectionID);
                s.setSectionLetter(letter);
                s.setTime(time);
                s.setLocation(location);
                s.setDept(dept);
                s.setCourseNum(courseNum);

                sections.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("DEBUG: getSections returned " + sections.size() + " sections:");
        for (Section s : sections) {
            System.out.println("  ID: " + s.getSectionID() + ", Section: " + s.getSectionLetter() + ", Time: " + s.getTime() + ", Location: " + s.getLocation());
        }
        return sections;
    }

    public void registerStudentForSection(int accountID, int sectionID) {
        String sql = "INSERT INTO Enrollment (accountID, sectionID, status) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            stmt.setInt(2, sectionID);
            stmt.setString(3, "registered");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<GradeList> getGradesForSection(int sectionID) {
        String sql =
            "SELECT a.accountID, a.fname, a.lname, g.asgmtID, g.score, asg.name " +
            "FROM Enrollment e " +
            "JOIN Account a ON e.accountID = a.accountID " +
            "LEFT JOIN Grade g ON g.accountID = a.accountID " +
            "LEFT JOIN Assignment asg ON asg.asgmtID = g.asgmtID " +
            "WHERE e.sectionID = ?";

        List<GradeList> rows = new ArrayList<>();
        Map<Integer, GradeList> map = new HashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int studentID = rs.getInt("accountID");
                String studentName = rs.getString("fname") + " " + rs.getString("lname");

                GradeList row = map.get(studentID);
                if (row == null) {
                    row = new GradeList(studentID, studentName);
                    map.put(studentID, row);
                    rows.add(row);
                }

                String asgmtName = rs.getString("name");
                // handle numeric types robustly
                Double score = null;
                Object scoreObj = rs.getObject("score");
                if (scoreObj != null) {
                    if (scoreObj instanceof Number) {
                        score = ((Number) scoreObj).doubleValue();
                    } else {
                        try {
                            score = Double.parseDouble(scoreObj.toString());
                        } catch (NumberFormatException ignored) {}
                    }
                }

                if (asgmtName != null) {
                    switch (asgmtName.toLowerCase()) {
                        case "asgmt1":
                            row.setAsgmt1(score);
                            break;
                        case "midterm":
                            row.setMidterm(score);
                            break;
                        case "asgmt2":
                            row.setAsgmt2(score);
                            break;
                        case "final":
                            row.setFinal(score);
                            break;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rows;
    }

    public void updateGrade(int studentID, int asgmtID, Double newScore) {
        if (newScore == null) return;
        String sql =
            "INSERT INTO Grade (asgmtID, accountID, score) VALUES (?, ?, ?) " +
            "ON CONFLICT(asgmtID, accountID) DO UPDATE SET score = excluded.score";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, asgmtID);
            ps.setInt(2, studentID);
            ps.setDouble(3, newScore);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, Integer> getAssignmentMap(int sectionID) {
        String sql = "SELECT asgmtID, name FROM Assignment WHERE sectionID = ?";
        Map<String, Integer> map = new HashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sectionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name").toLowerCase();
                int id = rs.getInt("asgmtID");
                map.put(name, id);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return map;
    }

    public List<Section> getSectionsTaughtByTeacher() {
        List<Section> sections = new ArrayList<>();
        System.out.println(currentAccountID); // debugging

        // debugging
        if (currentAccountID == -1) {
            System.err.println("getSectionsTaughtByTeacher method called before user login!");
            return sections;
        }

        String sql = "SELECT s.sectionID, s.courseID, s.profID, s.sectionLetter, s.time, s.location, " +
                     "s.status, c.courseNum, c.dept FROM Section s JOIN Course c ON " +
                     "s.courseID = c.courseID WHERE s.profID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, currentAccountID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Section s = new Section();
                s.setSectionID(rs.getInt("sectionID"));
                s.setCourseID(rs.getInt("courseID"));
                s.setProfID(rs.getInt("profID"));
                s.setSectionLetter(rs.getString("sectionLetter"));
                s.setTime(rs.getString("time"));
                s.setLocation(rs.getString("location"));
                s.setStatus(rs.getString("status"));
                s.setCourseNum(rs.getInt("courseNum"));
                s.setDept(rs.getString("dept"));

                sections.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("DEBUG: getSectionsTaughtByTeacher returned " + sections.size() + " sections for teacherID " + currentAccountID);
        for (Section s : sections) {
            System.out.println("  SectionID: " + s.getSectionID() + ", Letter: " + s.getSectionLetter() + ", Time: " + s.getTime() + ", Locaiton: " + s.getLocation());
        }
        return sections;
    }

    public String getCourseName(int courseID) {
        String sql = "SELECT courseName FROM Course WHERE courseID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("courseName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // or return "Unknown Course";
    }

    public String getTeacherName(int profID) {
        String sql = "SELECT fname, lname FROM Account WHERE accountID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String first = rs.getString("fname");
                    String last = rs.getString("lname");
                    return first + " " + last;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // or return "Unknown Teacher";
    }

    public String getCourseDescription(int courseID) {
        String sql = "SELECT description FROM Description WHERE courseID = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("description");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null; // no description found
    }
}