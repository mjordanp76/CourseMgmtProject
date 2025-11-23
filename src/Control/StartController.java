package Control;

import java.util.List;

import Boundary.LoginForm;
import Boundary.MainMenu;
import Boundary.MainMenuBuilder;
import Boundary.TeacherMenu;
import Boundary.StudentMenu;
import Entity.Account;
import Entity.Course;
import Entity.Section;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartController {
    
    private DBConnector dbConnector;
    private LoginControl loginControl;
    private Stage primaryStage;

    public StartController(Stage primaryStage) {
        dbConnector = new DBConnector();
        loginControl = new LoginControl(dbConnector);
        this.primaryStage = primaryStage;
    }

    public void initiate() {
        try {
            dbConnector.initializeDB();
            System.out.println("Database initialized.");//debugging
        } catch (Exception e) {
            System.err.println("Failed to initiate database: " + e.getMessage() + "\nExiting Course Management now.");
            e.printStackTrace();
            return; // stop startup if DB can't be initialized
        }

        LoginForm loginForm = new LoginForm(loginControl, primaryStage, this);
        loginControl.setLoginForm(loginForm);
        loginForm.display();

        // debugging
        dbConnector.getUser("hpotter@uni.edu");
    }

    public void onLoginSuccess(Account account) {
        loadMainMenu(account);
    }

    public void loadMainMenu(Account account) {

        // 1. Create the builder
        MainMenuBuilder builder = new MainMenuBuilder();

        // 2. Create the main menu wrapper
        MainMenu mainMenu = new MainMenu(builder, primaryStage);

        // 3. Set the user's displayed name
        mainMenu.setName(account.getFullName());

        // START HERE IN THE MORNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // 4. Create the student/teacher menu
        if (account.getRole().equals("teacher")) {
            TeacherMenu teacherMenu = new TeacherMenu(mainMenu);
            mainMenu.showDashboardView(teacherMenu.getDashboardTables());
        } else {
            StudentMenu studentMenu = new StudentMenu(mainMenu);

            // Fetch data
            List<Course> availableCourses = dbConnector.getCourses();
            List<Section> currentSchedule = dbConnector.getenrolledSections();

            // Populate tables
            studentMenu.getCourseTable().setItems(FXCollections.observableArrayList(availableCourses));
            studentMenu.getEnrolledTable().setItems(FXCollections.observableArrayList(currentSchedule));

            // Show tables
            mainMenu.showDashboardView(studentMenu.getDashboardTables());

        }

        // 5. Display it on the stage
        Scene scene = new Scene(mainMenu.getRoot(), 800, 600);
        primaryStage.setScene(scene);
    }

}
