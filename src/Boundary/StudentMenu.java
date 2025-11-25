package Boundary;

import Control.CourseController;
import Entity.Course;
import Entity.Section;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.util.List;

public class StudentMenu {

    private MainMenu mainMenu;
    private StudentMenuBuilder builder;
    private TableView<Course> courseTable;
    private TableView<Section> enrolledTable;
    private CourseController courseController;

    public StudentMenu(MainMenu mainMenu, CourseController courseController) {
        this.mainMenu = mainMenu;
        this.courseController = courseController;
        builder = new StudentMenuBuilder();
        courseTable = builder.createCourseTable();
        enrolledTable = builder.createEnrolledTable();
    }

    public TableView<Course> getCourseTable() {
        return courseTable;
    }

    public TableView<Section> getEnrolledTable() {
        return enrolledTable;
    }

    public VBox getDashboardTables() {
        return builder.buildDashboard(courseTable, enrolledTable);
    }

    private void setupSelectColumn() {
        TableColumn<Course, Void> selectCol = (TableColumn<Course, Void>) courseTable.getColumns().get(2);

        selectCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Select");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    btn.setOnAction(e -> {
                        System.out.print("Clicked!");
                        Course course = getTableView().getItems().get(getIndex());
                        courseController.openRegistrationForm(course);
                    });
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }


    public void populateTables(List<Course> availableCourses, List<Section> enrolledSections) {
        courseTable.setItems(FXCollections.observableArrayList(availableCourses));
        enrolledTable.setItems(FXCollections.observableArrayList(enrolledSections));
        Platform.runLater(this::setupSelectColumn);
    }

    // called by controller to refresh tables after registration
    public void updateTables(List<Course> availableCourses, List<Section> enrolledSections) {
        courseTable.setItems(FXCollections.observableArrayList(availableCourses));
        enrolledTable.setItems(FXCollections.observableArrayList(enrolledSections));
        Platform.runLater(this::setupSelectColumn);
    }
}