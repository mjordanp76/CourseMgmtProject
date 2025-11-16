package Boundary;

import Control.CourseController;
import Control.DBConnector;
import Control.LoginControl;
import Control.StartController;
import Entity.Account;
import Entity.Course;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TeacherMenu {
    
    private Stage stage;
    private Account account;
    private CourseController courseController;
    private LoginControl loginControl;
    private TableView<Course> available;

    public TeacherMenu(DBConnector dbConnector) {
        this.loginControl = new LoginControl(dbConnector, this);
    }

    public void show(Stage stage) {
        this.stage = stage;
        
        // will hold everything for student menu
        VBox mainMenu = new VBox(76);

        // create logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            System.out.println("Logging out...");
        });

        mainMenu.setStyle(
            // "-fx-border-color: gray;" +
            // "-fx-border-width: 2;" +
            // "-fx-border-radius: 5;" +
            //"-fx-background-color: white;" +
            "-fx-padding: 10;"
        );

        Label teacherLabel = new Label("Teacher Name Goes Here");
        teacherLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );
        
        // taught courses table
        TableView<Course> teachingTable = new TableView<>();

        TableColumn<Course, String> courseCol = new TableColumn<>("Class Name");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Course, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("prereq"));// this needs to access the status column of section table later

        TableColumn<Course, Void> buttonCol = new TableColumn<>("View/Update");
        buttonCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));
        buttonCol.setResizable(true);
        buttonCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Select");
            {
                btn.setOnAction(e -> {
                    Course course = getTableView().getItems().get(getIndex());
                    System.out.println("Selected: " + course.getCourseID());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null: btn);
                setAlignment(Pos.CENTER);
            }
        });

        teachingTable.getColumns().addAll(courseCol, statusCol, buttonCol);

        // add courses to table
        teachingTable.getItems().addAll(
            new Course("CSCI 1301", "Intro to Programming", "", ""),
            new Course("CSCI 1302", "Intro to Programming II", "", ""),
            new Course("CYBR 4700", "Software Engineering", "", "")
        );

        // wrappers for tables
        StackPane teachingWrapper = new StackPane(teachingTable);
        teachingWrapper.setPrefWidth(450);
        teachingWrapper.setMaxWidth(450);

        // set table/column widths
        // teachingTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        courseCol.setPrefWidth(150);
        statusCol.setPrefWidth(150);
        buttonCol.setPrefWidth(150);

        // 3. Put contentBox and Logout button in a GridPane
        GridPane wrapper = new GridPane();
        wrapper.setHgap(10);
        wrapper.setVgap(10);
        wrapper.setAlignment(Pos.CENTER);

        wrapper.add(mainMenu, 0, 0);
        GridPane.setHalignment(logoutBtn, HPos.RIGHT);
        GridPane.setValignment(logoutBtn, VPos.TOP);
        GridPane.setMargin(logoutBtn, new Insets(10, 10, 0, 0));
        wrapper.add(logoutBtn, 0, 0);

        // put everything into main menu box
        mainMenu.getChildren().addAll(teacherLabel, teachingWrapper);

        // center everything
        mainMenu.setAlignment(Pos.CENTER);
        centerTextColumn(courseCol);
        centerTextColumn(statusCol);

        // get computer display size
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        // compute __% of screen size
        double width = 800;
        double height = 800;

        Scene scene = new Scene(wrapper, width, height);
        stage.setScene(scene);
        stage.setTitle("Teacher Menu");
        stage.show();
    }

    private <S, T> void centerTextColumn(TableColumn<S, T> col) {
        col.setCellFactory(c -> new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
                setAlignment(Pos.CENTER);  // center cell content
            }
        });
        col.setStyle("-fx-alignment: CENTER;");  // center header
    }

}
