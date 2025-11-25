package Boundary;

import Entity.Course;
import Entity.Section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

public class StudentMenuBuilder {

    private VBox dashboardVBox;

    public StudentMenuBuilder() {
        dashboardVBox = new VBox(20); // spacing between tables
        dashboardVBox.setAlignment(Pos.TOP_CENTER);
    }

    public VBox buildDashboard(TableView<Course> courseTable, TableView<Section> enrolledTable) {
        // wrap the first table node in a VBox with a title
        Label table1Title = new Label("Available Courses");
        table1Title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        VBox table1Box = new VBox(5, table1Title, courseTable);
        table1Box.setAlignment(Pos.TOP_CENTER);
        table1Box.setMaxWidth(900);

        // wrap the second table node in a VBox with a title
        Label table2Title = new Label("Enrolled Courses");
        table2Title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        VBox table2Box = new VBox(5, table2Title, enrolledTable);
        table2Box.setAlignment(Pos.TOP_CENTER);
        table2Box.setMaxWidth(900);
        table2Box.setPadding(new Insets(0,0,20,0));

        dashboardVBox.getChildren().setAll(table1Box, table2Box);

        return dashboardVBox;
    }

    public VBox getDashboardVBox() {
        return dashboardVBox;
    }

    // helper method to create the first table with "Select" buttons
    public TableView<Course> createCourseTable() {
        TableView<Course> table = new TableView<>();
        table.setStyle("-fx-font-size: 16px;");

        TableColumn<Course, String> colCourseNumber = new TableColumn<>("Course Number");
        colCourseNumber.setCellValueFactory(new PropertyValueFactory<>("deptAndName"));
        colCourseNumber.setCellFactory(col -> {
            TableCell<Course, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Course, String> colCourseName = new TableColumn<>("Course Name");
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colCourseName.setCellFactory(col -> {
            TableCell<Course, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Course, Void> colSelect = new TableColumn<>("");
        colSelect.setCellFactory(col -> {
            TableCell<Course, Void> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        colCourseNumber.setPrefWidth(199);
        colCourseName.setPrefWidth(500);
        colSelect.setPrefWidth(199);

        table.getColumns().addAll(colCourseNumber, colCourseName, colSelect);
        return table;
    }

    // helper method to create the second table (no buttons yet)
    public TableView<Section> createEnrolledTable() {
        TableView<Section> table = new TableView<>();
        table.setStyle("-fx-font-size: 16px;");

        TableColumn<Section, String> col1 = new TableColumn<>("Course Number");
        col1.setCellValueFactory(new PropertyValueFactory<>("deptAndNum"));
        col1.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, String> col2 = new TableColumn<>("Course Name");
        col2.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        col2.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, String> col3 = new TableColumn<>("Time");
        col3.setCellValueFactory(new PropertyValueFactory<>("time"));
        col3.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, String> col4 = new TableColumn<>("Location");
        col4.setCellValueFactory(new PropertyValueFactory<>("location"));
        col4.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        col1.setPrefWidth(179);
        col2.setPrefWidth(270);
        col3.setPrefWidth(270);
        col4.setPrefWidth(179);

        table.getColumns().addAll(col1, col2, col3, col4);

        return table;
    }
}