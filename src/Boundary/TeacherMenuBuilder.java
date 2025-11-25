package Boundary;

import Entity.Course;
import Entity.Section;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TeacherMenuBuilder {

    private VBox dashboardVBox;

    public TeacherMenuBuilder() {
        dashboardVBox = new VBox(20); // spacing between tables
        dashboardVBox.setAlignment(Pos.TOP_CENTER);
    }

    public VBox buildDashboard(TableView<Section> sectionTable) {
        // Wrap the table node in a VBox with a title
        Label tableTitle = new Label("My Courses");
        tableTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        VBox tableBox = new VBox(5, tableTitle, sectionTable);
        tableBox.setAlignment(Pos.TOP_CENTER);

        dashboardVBox.getChildren().setAll(tableBox);

        return dashboardVBox;
    }

    public VBox getDashboardVBox() {
        return dashboardVBox;
    }

    // Helper method to create the first table with "Select" buttons
    public TableView<Section> createSectionTable() {
        TableView<Section> table = new TableView<>();

        TableColumn<Section, String> courseNameCol = new TableColumn<>("Class Name");
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseAndSection"));
        courseNameCol.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, String> courseStatusCol = new TableColumn<>("Status");
        courseStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        courseStatusCol.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, Void> selectCol = new TableColumn<>("View/Update");
        selectCol.setCellFactory(col -> {
            TableCell<Section, Void> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        courseNameCol.setPrefWidth(250);
        courseStatusCol.setPrefWidth(250);
        selectCol.setPrefWidth(250);

        table.getColumns().addAll(courseNameCol, courseStatusCol, selectCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }
}
