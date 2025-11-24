package Control;

import Boundary.MainMenu;
import Boundary.RegistrationForm;
import Boundary.StudentMenu;
import Entity.Course;
import Entity.Section;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class CourseController {

    private DBConnector db;
    private StudentMenu studentMenu;
    private int accountID;
    private MainMenu mainMenu;

    public CourseController(MainMenu mainMenu, DBConnector db) {
        this.mainMenu = mainMenu;
        this.db = db;

        // Connect the menu's select button to this controller
        // studentMenu.setOnCourseSelected(this::openRegistrationForm);
    }

    public void setStudentMenu(StudentMenu studentMenu) {
        this.studentMenu = studentMenu;
    }

    // StartController makes courseController before mainMenu, so this injects mainMenu later
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setAccountID(int id) {
        this.accountID = id;
    }

    public void openRegistrationForm(Course course) {
        System.out.println("openRegistrationForm CALLED for: " + course.getCourseName());
        setAccountID(accountID);
        System.out.println("accountID = " + accountID);

        // 1. Get sections from db
        List<Section> sections = db.getSections(course.getCourseID());
        System.out.println("Sections loaded: " + sections.size());

        // dummy course description
        String description = "This class will rock your world!";

        // 2. Create RegistrationForm pop-up
        RegistrationForm regForm = new RegistrationForm();
        Parent formRoot = regForm.display(course.getCourseName(), description);
        formRoot.setUserData(regForm);
        Stage popUp = new Stage();
        
        System.out.println("mainMenu stage = " + mainMenu);
        popUp.initOwner(mainMenu.getStage()); // keep main menu visible
        popUp.initModality(Modality.APPLICATION_MODAL); // optional: blocks main menu interaction
        popUp.setTitle("Register for " + course.getCourseName());

        System.out.println("formRoot = " + formRoot);
        regForm.fillTable(sections);
        System.out.println("Table filled");

        // 3. Set up Register button callback
        regForm.setOnRegister(section -> {
            System.out.println("Register clicked!");
            // Insert registration via db
            db.registerStudentForSection(accountID, section.getSectionID());

            // Close pop-up
            popUp.close();

            // Refresh main menu tables
            List<Course> newAvailable = db.getCourses();
            List<Section> newEnrolled = db.getenrolledSections();
            studentMenu.updateTables(newAvailable, newEnrolled);
        });

        popUp.setScene(new Scene(formRoot, 700, 700));

        System.out.println("Showing popup...");
        popUp.showAndWait();
    }
}