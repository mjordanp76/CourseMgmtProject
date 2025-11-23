package Boundary;

import Entity.Course;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TeacherMenu {

    private StudentMenuBuilder builder;
    private MainMenu mainMenu;

    public TeacherMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        builder = new StudentMenuBuilder();
    }

    public Node getDashboardTables() {
        // Create tables using the builder
        TableView<Course> courseTable = builder.createCourseTable();
        TableView<Course> enrolledTable = builder.createEnrolledTable();

        // Set up "Select" button actions for the first table
        TableColumn<Course, Void> selectCol = (TableColumn<Course, Void>) courseTable.getColumns().get(2);

        Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Select");

            {
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setOnAction(e -> {
                    // Create RegistrationForm and inject it into MainMenu
                    RegistrationForm regForm = new RegistrationForm();
                    Node formNode = regForm.display(
                        "Course Sections",
                        "Select a section to register"
                    );
                    mainMenu.showDetailView(formNode, "Course: Dummy Course"); // replace with actual course later
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };

        selectCol.setCellFactory(cellFactory);

        // Return the dashboard VBox with both tables
        return builder.buildDashboard(courseTable, enrolledTable);
    }
}
