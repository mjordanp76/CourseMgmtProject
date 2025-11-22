package Control;

import Boundary.LoginForm;

import javafx.stage.Stage;

public class StartController {
    
    private DBConnector dbConnector;
    private LoginControl loginControl;

    public StartController() {
        dbConnector = new DBConnector();
        loginControl = new LoginControl(dbConnector);
    }

    public void initiate(Stage primaryStage) {
        try {
            dbConnector.initializeDB();
            System.out.println("Database initialized.");//debugging
        } catch (Exception e) {
            System.err.println("Failed to initiate database: " + e.getMessage() + "\nExiting Course Management now.");
            e.printStackTrace();
            return; // stop startup if DB can't be initialized
        }

        LoginForm loginForm = new LoginForm(loginControl, primaryStage);
        loginControl.setLoginForm(loginForm);
        loginForm.display();

        // debugging
        dbConnector.getUser("hpotter@uni.edu");
    }
}
