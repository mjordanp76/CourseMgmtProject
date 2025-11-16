package Control;

import Boundary.ClassView;
import Boundary.LoginForm;
import Boundary.RegistrationForm;
import Boundary.StudentMenu;
import Boundary.TeacherMenu;
import Entity.Account;

import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class LoginControl {
    
    private DBConnector db;
    private LoginForm loginForm;
    private StudentMenu studentMenu;
    private RegistrationForm registrationForm;
    private TeacherMenu teacherMenu;
    private ClassView classView;
    
    // change StudentMenu studentMenu back to LoginForm loginForm when done
    public LoginControl(DBConnector dbConnector, ClassView classView) {
        this.db = dbConnector;
        this.teacherMenu = teacherMenu;
    }

    public boolean login(String usn, String pwd) {
        // validate input
        if (!validateInput(usn, pwd)) {
            loginForm.displayError("Username must be an email address.");
            return false;
        };

        // hash the password
        String hashedPW = hashPassword(pwd);
        System.out.println("Plaintext password: " + pwd);
        System.out.println("Password SHA-256 hash: " + hashedPW);

        // get account from DB
        Account account = db.getUser(usn);
        if (account == null) {
            loginForm.displayError("User not found.");
            return false;
        };

        // authenticate & save login
        if (!authenticate(account, hashedPW)) {
            return false;
        } else {
            db.saveLogin(usn);
            System.out.print("Successful login!");
            System.out.println("Login saved to database.");
        };

        // get course list

        // show next menu

        // close login form
        
        return true;
    }

    private boolean validateInput(String usn, String pwd) {
        final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9]+@[A-Za-z]+\\.edu$", Pattern.CASE_INSENSITIVE);

        if (usn == null || pwd == null) return false;
        return emailPattern.matcher(usn).matches();
    }

    private String hashPassword(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean authenticate(Account account, String hashedPW) {
        if(account.getPswdHash().equals(hashedPW)) {
            return true;
        } else {
            loginForm.displayError("Invalid username or password.");
            return false;
        }
    }

}
