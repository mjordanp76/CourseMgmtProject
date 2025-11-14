package Control;

import Boundary.LoginForm;

import javafx.stage.Stage;

public class StartController {
    
    private DBConnector dbConnector;
    private Stage primaryStage;

    public StartController() {
        dbConnector = new DBConnector();
    }

    public void initiate(Stage primaryStage) {
        try {
            dbConnector.initializeDB();
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage() + " Exiting Course Management now.");
            e.printStackTrace();
            return; // stop startup if DB can't be initialized
        }
        LoginForm loginForm = new LoginForm(this);
        loginForm.show(primaryStage);
    }

    /*
     * Used by other controllers to access the DB
     * Avoids having to create DB connections all over the place
     */
    public DBConnector getDbConnector() {
        return dbConnector;
    }
}
