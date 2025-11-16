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

public class StudentMenu {
    
    private Stage stage;
    private Account account;
    private CourseController courseController;
    private LoginControl loginControl;
    private TableView<Course> available;

    public StudentMenu(DBConnector dbConnector) {
        this.loginControl = new LoginControl(dbConnector, this);
    }

    public void show(Stage stage) {
        this.stage = stage;
        
        // will hold everything for student menu
        VBox mainMenu = new VBox(7);

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

        Label studNameLabel = new Label("Student Name Goes Here");
        studNameLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );

        Label majorLabel = new Label("Major Goes Here");
        majorLabel.setPadding(new Insets(0,0,21,0));
        majorLabel.setStyle(
            "-fx-font-size: 16px;"
        );

        Label availCoursesLabel = new Label("Available Courses");
        availCoursesLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );

        Label regCoursesLabel = new Label("Registered Courses");
        regCoursesLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );
        
        // available courses table
        TableView<Course> availTable = new TableView<>();

        TableColumn<Course, String> numCol = new TableColumn<>("Course Number");
        numCol.setCellValueFactory(new PropertyValueFactory<>("courseID"));

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("CourseName"));

        TableColumn<Course, Void> buttonCol = new TableColumn<>("");
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

        availTable.getColumns().addAll(numCol, nameCol, buttonCol);

        // add courses to table
        availTable.getItems().addAll(
            new Course("CSCI 1301", "Intro to Programming", "", ""),
            new Course("CSCI 1302", "Intro to Programming II", "", ""),
            new Course("CYBR 4700", "Software Engineering", "", "")
        );

        // registered courses table
        TableView<Course> regTable = new TableView<>();

        TableColumn<Course, String> numColReg = new TableColumn<>("Course Number");
        numColReg.setCellValueFactory(new PropertyValueFactory<>("courseID"));

        TableColumn<Course, String> nameColReg = new TableColumn<>("Course Name");
        nameColReg.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Course, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("courseTime"));

        TableColumn<Course, String> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new PropertyValueFactory<>("courseLocation"));

        regTable.getColumns().addAll(numColReg, nameColReg, timeCol, locCol);

        // add courses to table
        regTable.getItems().addAll(
            new Course("CSCI 4531", "Malware Analysis", "M/W 2:30 - 3:45", "GCC 2300")
        );

        // wrappers for tables
        StackPane availWrapper = new StackPane(availTable);
        availWrapper.setPrefWidth(550);
        availWrapper.setMaxWidth(550);
        StackPane regWrapper = new StackPane(regTable);
        regWrapper.setPrefWidth(550);
        regWrapper.setMaxWidth(550);

        // set table/column widths
        // availTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        numCol.setPrefWidth(100);
        nameCol.setPrefWidth(350);
        buttonCol.setPrefWidth(100);

        // regTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        numColReg.setPrefWidth(100);
        nameColReg.setPrefWidth(200);
        timeCol.setPrefWidth(150);
        locCol.setPrefWidth(100);

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
        mainMenu.getChildren().addAll(studNameLabel, majorLabel, availCoursesLabel, availWrapper, regCoursesLabel, regWrapper);

        // center everything
        mainMenu.setAlignment(Pos.CENTER);
        centerTextColumn(numCol);
        centerTextColumn(nameCol);
        centerTextColumn(numColReg);
        centerTextColumn(nameColReg);
        centerTextColumn(timeCol);
        centerTextColumn(locCol);

        // get computer display size
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        // compute __% of screen size
        double width = 800;
        double height = 800;

        Scene scene = new Scene(wrapper, width, height);
        stage.setScene(scene);
        stage.setTitle("Student Menu");
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
