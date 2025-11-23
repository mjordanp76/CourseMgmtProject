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

    public CourseController(DBConnector db, StudentMenu studentMenu, MainMenu mainMenu, int accountID) {
        this.db = db;
        this.studentMenu = studentMenu;
        this.mainMenu = mainMenu;
        this.accountID = accountID;

        // Connect the menu's select button to this controller
        studentMenu.setOnCourseSelected(this::openRegistrationForm);
    }

    public void openRegistrationForm(Course course) {
        // 1. Get sections from DBConnector
        List<Section> sections = db.getSections(course.getCourseNum());

        // 2. Create RegistrationForm pop-up
        RegistrationForm regForm = new RegistrationForm();
        Stage popUp = new Stage();
        popUp.initOwner(mainMenu.getStage()); // keep main menu visible
        popUp.initModality(Modality.APPLICATION_MODAL); // optional: blocks main menu interaction
        popUp.setTitle("Register for " + course.getCourseName());

        // dummy course description
        String description = "This class will rock your world!";
        Parent formRoot = regForm.display(course.getCourseName(), description); // set up descriptions somehwere
        regForm.fillTable(sections);

        // 3. Set up Register button callback
        regForm.setOnRegister(section -> {
            int sectionID = section.getSectionID();
            // Insert registration via DBConnector
            db.registerStudentForSection(accountID, sectionID);

            // Close pop-up
            popUp.close();

            // Refresh main menu tables
            List<Course> updatedAvailable = db.getCourses();
            List<Section> updatedSchedule = db.getenrolledSections();
            studentMenu.updateTables(updatedAvailable, updatedSchedule);
        });

        popUp.setScene(new Scene(formRoot, 700, 700));
        popUp.show();
    }
}
