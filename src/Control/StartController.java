package Control;

import Boundary.ClassView;
import Boundary.LoginForm;
import Boundary.RegistrationForm;
import Boundary.StudentMenu;
import Boundary.TeacherMenu;
import javafx.stage.Stage;

public class StartController {
    
    private DBConnector dbConnector;

    public StartController() {
        dbConnector = new DBConnector();
    }

    public void initiate(Stage primaryStage) {
        try {
            dbConnector.initializeDB();
            System.out.println("Starting up ... database initialized.");// FOR DEBUGGING
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage() + " Exiting Course Management now.");
            e.printStackTrace();
            return; // stop startup if DB can't be initialized
        }
        // LoginForm loginForm = new LoginForm(dbConnector);
        // loginForm.show(primaryStage);

        // StudentMenu studentMenu = new StudentMenu(dbConnector);
        // studentMenu.show(primaryStage);

        // RegistrationForm registrationForm = new RegistrationForm(dbConnector);
        // registrationForm.show(primaryStage);

        // TeacherMenu teacherMenu = new TeacherMenu(dbConnector);
        // teacherMenu.show(primaryStage);

        ClassView classView = new ClassView(dbConnector);
        classView.show(primaryStage);
    }

    /*
     * Used by other controllers to access the DB
     * Avoids having to create DB connections all over the place
     */
    public DBConnector getDbConnector() {
        return dbConnector;
    }
}
