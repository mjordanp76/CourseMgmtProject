package Control;

import Boundary.MainMenu;
import Boundary.RegistrationForm;
import Boundary.StudentMenu;
import Entity.Course;
import Entity.Section;

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
    }

    public void setStudentMenu(StudentMenu studentMenu) {
        this.studentMenu = studentMenu;
    }

    // startController makes courseController before mainMenu, so this injects mainMenu later
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

        // get sections from db
        List<Section> sections = db.getSections(course.getCourseID());
        System.out.println("Sections loaded: " + sections.size());

        // fetchcourse description
        int id = course.getCourseID();
        String description = db.getCourseDescription(id);

        // create RegistrationForm pop-up
        RegistrationForm regForm = new RegistrationForm();
        Parent formRoot = regForm.display(course.getCourseName(), description);
        formRoot.setUserData(regForm);
        Stage popUp = new Stage();
        
        System.out.println("mainMenu stage = " + mainMenu);
        popUp.initOwner(mainMenu.getStage()); // keep main menu visible
        popUp.initModality(Modality.APPLICATION_MODAL); // optional: blocks main menu interaction
        popUp.setTitle("Sections for " + course.getCourseName());

        System.out.println("formRoot = " + formRoot);
        regForm.fillTable(sections);
        System.out.println("Table filled");

        // set up Register button callback
        regForm.setOnRegister(section -> {
            System.out.println("Register clicked!");
            // insert registration via db
            db.registerStudentForSection(accountID, section.getSectionID());

            // close pop-up
            popUp.close();

            // refresh main menu tables
            List<Course> newAvailable = db.getCourses();
            List<Section> newEnrolled = db.getenrolledSections();
            studentMenu.updateTables(newAvailable, newEnrolled);
        });

        popUp.setScene(new Scene(formRoot, 900, 720));

        System.out.println("Showing popup...");
        popUp.showAndWait();
    }
}