package Control;

import java.util.List;

import Boundary.LoginForm;
import Boundary.MainMenu;
import Boundary.MainMenuBuilder;
import Boundary.TeacherMenu;
import Boundary.StudentMenu;
import Control.CourseController;
import Control.DBConnector;
import Control.LoginControl;
import Control.TeacherController;
import Entity.Account;
import Entity.Course;
import Entity.Section;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartController {

    private Stage primaryStage;
    private DBConnector db;
    private LoginControl loginControl;
    private CourseController courseController;
    private TeacherController teacherController;
    private MainMenu mainMenu;
    private MainMenuBuilder mainMenuBuilder;

    public StartController(Stage primaryStage, DBConnector db) {
        this.primaryStage = primaryStage;
        this.db = db;
    }

    public void initiate() {
        try {
            db.initializeDB();
            System.out.println("Database initialized.");//debugging
        } catch (Exception e) {
            System.err.println("Failed to initiate database: " + e.getMessage() + "\nExiting Course Management now.");
            e.printStackTrace();
            return; // stop startup if DB can't be initialized
        }

        // create all controllers
        loginControl = new LoginControl(null, db);
        courseController = new CourseController(null, db);
        teacherController = new TeacherController(null, db);

        // create login form
        LoginForm loginForm = new LoginForm(loginControl, primaryStage, this);
    
        // inject loginForm into loginControl
        loginControl.setLoginForm(loginForm);

        loginForm.display();

        // debugging
        db.getUser("hpotter@uni.edu");
    }

    public void onLoginSuccess(Account account) {
        loadMainMenu(account);
        courseController.setAccountID(account.getAccountID());
    }

    public void loadMainMenu(Account account) {

        // 1. Create the builder
        MainMenuBuilder builder = new MainMenuBuilder();

        // 2. Create the main menu wrapper
        MainMenu mainMenu = new MainMenu(builder, primaryStage);

        // 3. Set the user's displayed name and major
        mainMenu.setName(account.getFullName());

        // 4. Create the student/teacher menu
        if (account.getRole().equals("teacher")) {
            TeacherMenu teacherMenu = new TeacherMenu(mainMenu, teacherController);
            teacherController.setTeacherMenu(teacherMenu);
            // fetch data
            List<Section> taughtCourSections = db.getSectionsTaughtByTeacher();
            // populate table
            teacherMenu.populateTables(taughtCourSections);
            // show table
            mainMenu.showDashboardView(teacherMenu.getDashboardTables());
        } else {
            StudentMenu studentMenu = new StudentMenu(mainMenu, courseController);
            courseController.setStudentMenu(studentMenu);
            builder.setDept(account.getDept());

            // Fetch data
            List<Course> availableCourses = db.getCourses();
            List<Section> currentSchedule = db.getenrolledSections();

            // Populate tables
            studentMenu.populateTables(availableCourses, currentSchedule);

            // Show tables
            mainMenu.showDashboardView(studentMenu.getDashboardTables());
        }

        // 5. Display it on the stage
        Scene scene = new Scene(mainMenu.getRoot(), 1000, 800);
        primaryStage.setScene(scene);

        // get button from main menu so controller can set logic
        Button logoutBtn = mainMenu.getLogoutButton();
        if (account.getRole().equals("teacher")) {
            teacherController.setMainMenu(mainMenu);
        } else {
            courseController.setMainMenu(mainMenu);
        }
        
        // logout button logic
        logoutBtn.setOnAction(e -> {
            // 1. Save logout in Session table
            db.saveLogout(account.getAccountID(), "logout");;

            // 2. Close the menu window
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.close();

            // 3. Show the login popup again
            LoginForm login = new LoginForm(loginControl, primaryStage, this);
            login.display();
        });
    }
}