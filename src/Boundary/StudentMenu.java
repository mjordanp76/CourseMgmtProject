package Boundary;

import Entity.Course;
import Entity.Section;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;
import java.util.function.Consumer;

public class StudentMenu {

    private MainMenu mainMenu;
    private StudentMenuBuilder builder;
    private TableView<Course> courseTable;
    private TableView<Section> enrolledTable;

    private Consumer<Course> onCourseSelected;

    public StudentMenu(MainMenu mainMenu) {
        //this.mainMenu = mainMenu;
        builder = new StudentMenuBuilder();
        courseTable = builder.createCourseTable();
        enrolledTable = builder.createEnrolledTable();
        
    }

    public void setOnCourseSelected(Consumer<Course> callback) {
        this.onCourseSelected = callback;
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

    private void setupSelectButtons(TableView<Course> courseTable) {
        TableColumn<Course, Void> selectCol = (TableColumn<Course, Void>) courseTable.getColumns().get(2);

        selectCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Select");

            {
                btn.setOnAction(e -> {
                    RegistrationForm regForm = new RegistrationForm();
                    Node formNode = regForm.display(
                        "Course Sections",
                        "Select a section to register"
                    );
                    mainMenu.showDetailView(formNode, "Course: TODO");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    public void populateTables(List<Course> availableCourses, List<Section> enrolledSections) {
        courseTable.setItems(FXCollections.observableArrayList(availableCourses));
        enrolledTable.setItems(FXCollections.observableArrayList(enrolledSections));
    }

    // Called by controller to refresh tables after registration
    public void updateTables(List<Course> availableCourses, List<Section> enrolledSections) {
        courseTable.setItems(FXCollections.observableArrayList(availableCourses));
        enrolledTable.setItems(FXCollections.observableArrayList(enrolledSections));
    }
}
