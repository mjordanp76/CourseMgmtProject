package Boundary;

import Control.DBConnector;
import Control.LoginControl;
import Control.StartController;
import Entity.Account;
import Entity.GradeList;
import Entity.GradeList;
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

public class ClassView {

    private Stage stage;
    private Account account;
    private LoginControl loginControl;
    private TableView<GradeList> available;
    private TableView<GradeList> gradeList;

    public ClassView(DBConnector dbConnector) {
        this.loginControl = new LoginControl(dbConnector, this);
    }

    public void show(Stage stage) {
        this.stage = stage;
        
        // will hold everything for student menu
        VBox mainMenu = new VBox(25);

        // create submit button
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> {
            System.out.println("Submitting grades ...");
        });

        mainMenu.setStyle(
            // "-fx-border-color: gray;" +
            // "-fx-border-width: 2;" +
            // "-fx-border-radius: 5;" +
            //"-fx-background-color: white;" +
            "-fx-padding: 10;"
        );

        Label classLabel = new Label("Intro to Programming II");
        classLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );

        Label teacherLabel = new Label("Dr. Strange");
        teacherLabel.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );
        
        // section grade table
        // TODO: make sure they all show correct information later
        TableView<GradeList> gradeTable = new TableView<>();

        TableColumn<GradeList, String> studCol = new TableColumn<>("Student Name");
        studCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<GradeList, String> assign1Col = new TableColumn<>("Assignment #1");
        assign1Col.setCellValueFactory(new PropertyValueFactory<>("prereq"));

        TableColumn<GradeList, String> midCol = new TableColumn<>("Midterm");
        midCol.setCellValueFactory(new PropertyValueFactory<>("prereq"));

        TableColumn<GradeList, String> assign2Col = new TableColumn<>("Assignment #2");
        assign2Col.setCellValueFactory(new PropertyValueFactory<>("prereq"));

        TableColumn<GradeList, String> finalCol = new TableColumn<>("Final");
        finalCol.setCellValueFactory(new PropertyValueFactory<>("prereq"));

        TableColumn<GradeList, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("prereq"));

        gradeTable.getColumns().addAll(studCol, assign1Col, midCol, assign2Col, finalCol, totalCol);

        // add courses to table
        gradeTable.getItems().addAll(
            new GradeList(0, 0, 0, 90),
            new GradeList(0, 0, 0, 92.5),
            new GradeList(0, 0, 0, 98.6)
        );

        // wrappers for tables
        StackPane gradeWrapper = new StackPane(gradeTable);
        gradeWrapper.setPrefWidth(600);
        gradeWrapper.setMaxWidth(600);

        // set table/column widths
        // gradeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        studCol.setPrefWidth(100);
        assign1Col.setPrefWidth(100);
        midCol.setPrefWidth(100);
        assign2Col.setPrefWidth(100);
        finalCol.setPrefWidth(100);
        totalCol.setPrefWidth(100);

        // 3. Put contentBox and Logout button in a GridPane
        GridPane wrapper = new GridPane();
        wrapper.setHgap(10);
        wrapper.setVgap(10);
        wrapper.setAlignment(Pos.CENTER);

        wrapper.add(mainMenu, 0, 0);
        GridPane.setHalignment(submitBtn, HPos.RIGHT);
        GridPane.setValignment(submitBtn, VPos.BOTTOM);
        GridPane.setMargin(submitBtn, new Insets(0, 10, 10, 0));
        wrapper.add(submitBtn, 0, 1);

        // put everything into main menu box
        mainMenu.getChildren().addAll(classLabel, teacherLabel, gradeWrapper);

        // center everything
        mainMenu.setAlignment(Pos.CENTER);
        centerTextColumn(studCol);
        centerTextColumn(assign1Col);

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
