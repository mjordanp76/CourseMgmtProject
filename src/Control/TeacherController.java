package Control;

import Boundary.ClassView;
import Boundary.MainMenu;
import Boundary.TeacherMenu;
import Entity.GradeList;
import Entity.Section;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class TeacherController {

    private DBConnector db;
    private TeacherMenu teacherMenu;
    private int accountID;
    private MainMenu mainMenu;

    public TeacherController(MainMenu mainMenu, DBConnector db) {
        this.mainMenu = mainMenu;
        this.db = db;
    }

    public void setTeacherMenu(TeacherMenu teacherMenu) {
        this.teacherMenu = teacherMenu;
    }

    // StartController makes courseController before mainMenu, so this injects mainMenu later
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setAccountID(int id) {
        this.accountID = id;
    }

    // open the class view popup for a given section
    public void openClassView(Section section) {
        List<GradeList> rows = db.getGradesForSection(section.getSectionID());

        String courseName = db.getCourseName(section.getCourseID());
        String teacherName = db.getTeacherName(section.getProfID());

        ClassView cv = new ClassView();
        Parent root = cv.display(courseName, teacherName);
        Stage popup = new Stage();
        popup.initOwner(mainMenu.getStage());
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Grades — " + section.getCourseNum() + " " + section.getSectionLetter());
        cv.fillTable(rows);

        // map assignment name -> asgmtID
        Map<String, Integer> asgmtIDs = db.getAssignmentMap(section.getSectionID());

        cv.setOnSave(updatedRows -> {
            for (GradeList r : updatedRows) {

                if (asgmtIDs.containsKey("asgmt1") && r.getAsgmt1() != null)
                    db.updateGrade(r.getStudentID(), asgmtIDs.get("asgmt1"), r.getAsgmt1());

                if (asgmtIDs.containsKey("midterm") && r.getMidterm() != null)
                    db.updateGrade(r.getStudentID(), asgmtIDs.get("midterm"), r.getMidterm());

                if (asgmtIDs.containsKey("asgmt2") && r.getAsgmt2() != null)
                    db.updateGrade(r.getStudentID(), asgmtIDs.get("asgmt2"), r.getAsgmt2());

                if (asgmtIDs.containsKey("final") && r.getFinal() != null)
                    db.updateGrade(r.getStudentID(), asgmtIDs.get("final"), r.getFinal());
            }

            // close the popup and refresh teacher menu if present
            popup.close();
            if (teacherMenu != null) {
                List<Section> updatedSections = db.getSectionsTaughtByTeacher();
                teacherMenu.updateTables(updatedSections);
            }
        });

        popup.setScene(new Scene(root, 900, 600));
        popup.showAndWait();
    }
}
