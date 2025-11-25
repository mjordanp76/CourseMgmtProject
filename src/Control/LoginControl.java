package Control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import Boundary.LoginForm;
import Entity.Account;

public class LoginControl {

    private DBConnector db;
    private LoginForm loginForm;

    public LoginControl(LoginForm loginForm, DBConnector db) {
        this.loginForm = loginForm;
        this.db = db;
    }

    // StartController makes loginControl before loginForm, so this injects loginForm later
    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }

    public Account login(String usn, String pwd) {
        // validate input
        if(!validate(usn, pwd)) {
            loginForm.displayError("Username must be an email address.");
            return null;
        }

        // get user account
        Account account = db.getUser(usn);
        if (account == null) {
            loginForm.displayError("User not found.");
            return null;
        }

        // hash the entered password
        String hashedPW = hashPassword(pwd);

        // authenticate credentials & save login
        if (authenticate(hashedPW, account)) {
            db.saveLogin(usn);
            System.out.println("Successful login! Login saved to database."); // TODO: maybe move this message to saveLogin()
            return account;
        } else {
            return null;
        }
    }

    private boolean validate(String usn, String pwd) {
        final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9]+@[A-Za-z]+\\.edu$", Pattern.CASE_INSENSITIVE);
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
            return hexString.toString(); // SHA-256 hash of entered password
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean authenticate(String hashedPW, Account account) {
        if(account.getPwdHash().equals(hashedPW)) return true;
        else {
            loginForm.displayError("Invalid username or password.");
            return false;
        }
    }
}
