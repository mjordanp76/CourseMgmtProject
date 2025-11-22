package Control;

import Boundary.LoginForm;
import Boundary.MainMenu;
import Boundary.MainMenuBuilder;
import Entity.Account;
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
        MainMenu mainMenu = new MainMenu(builder);

        // 3. Set the user's displayed name
        mainMenu.setName(account.getFullName());

        // // 4. TEST: create a dummy content node
        // VBox dummyContent = new VBox();
        // dummyContent.getChildren().add(new Label("This is the test content area."));
        // dummyContent.setSpacing(10);

        // // 5. Show the dashboard view (this will show the name and logout button)
        // mainMenu.showDashboardView(dummyContent);

        4. Create the student/teacher menu
        if (account.isTeacher()) {
            TeacherMenu teacherMenu = new TeacherMenu(mainMenu);
            mainMenu.showDashboardView(teacherMenu.getDashboardTables());
        } else {
            StudentMenu studentMenu = new StudentMenu(mainMenu);
            mainMenu.showDashboardView(studentMenu.getDashboardTables());
        }

        // 5. Display it on the stage
        Scene scene = new Scene(mainMenu.getRoot(), 800, 600);
        primaryStage.setScene(scene);
    }

}
