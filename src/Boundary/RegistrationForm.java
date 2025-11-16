package Boundary;

import Control.CourseController;
import Control.DBConnector;
import Control.LoginControl;
import Control.StartController;
import Entity.Account;
import Entity.Section;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RegistrationForm {

    private Stage stage;
    private Account account;
    private CourseController courseController;
    private LoginControl loginControl;
    private TableView<Section> available;

    public RegistrationForm(DBConnector dbConnector) {
        this.loginControl = new LoginControl(dbConnector, this);
    }

    public void show(Stage stage) {
        this.stage = stage;
        
        // will hold everything for student menu
        VBox regisMenu = new VBox(10);

        // create logout button
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            System.out.println("Logging out...");
        });

        regisMenu.setStyle(
            // "-fx-border-color: gray;" +
            // "-fx-border-width: 2;" +
            // "-fx-border-radius: 5;" +
            //"-fx-background-color: white;" +
            "-fx-padding: 10;"
        );

        Label courseNameLabel = new Label("Intro to Programming");
        courseNameLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );

        Label descripLabel = new Label("Course Description");
        descripLabel.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );
        
        Label descripTextLabel = new Label("Here is where a short description of the course will appear. It will consist of 2-4 sentences describing the main goal of the class so students have a better understanding of what the expect in the class before they commit to registering.");
        descripTextLabel.setWrapText(true);
        descripTextLabel.setTextAlignment(TextAlignment.CENTER);
        descripTextLabel.setStyle(
            "-fx-font-size: 16px;"
        );
        
        // available sections table
        TableView<Section> secTable = new TableView<>();

        TableColumn<Section, String> secCol = new TableColumn<>("Section");
        secCol.setCellValueFactory(new PropertyValueFactory<>("sectionLetter"));

        TableColumn<Section, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Section, String> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Section, Void> buttonCol = new TableColumn<>("");
        buttonCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));
        buttonCol.setResizable(true);
        buttonCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Register");
            {
                btn.setOnAction(e -> {
                    Section section = getTableView().getItems().get(getIndex());
                    System.out.println("Registered for: " + section.getSectionID());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null: btn);
                setAlignment(Pos.CENTER);
            }
        });

        secTable.getColumns().addAll(secCol, timeCol, locCol, buttonCol);

        // add sections to table
        secTable.getItems().addAll(
            new Section(0, 0,"A", "M/W 11-12:15", "UH 120", ""),
            new Section(0, 0, "B", "T/Th 11-12:15", "UH 120", ""),
            new Section(0, 0, "C", "M/W/F 11-11:55", "UH 124", "")
        );

        // wrappers for tables
        StackPane regisWrapper = new StackPane(secTable);
        regisWrapper.setPrefWidth(550);
        regisWrapper.setMaxWidth(550);

        // set table/column widths
        // secTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        descripTextLabel.setPrefWidth(550);
        secCol.setPrefWidth(100);
        timeCol.setPrefWidth(180);
        locCol.setPrefWidth(180);
        buttonCol.setPrefWidth(90);

        // 3. Put contentBox and Logout button in a GridPane
        GridPane wrapper = new GridPane();
        wrapper.setHgap(10);
        wrapper.setVgap(10);
        wrapper.setAlignment(Pos.CENTER);

        wrapper.add(regisMenu, 0, 0);
        GridPane.setHalignment(logoutBtn, HPos.RIGHT);
        GridPane.setValignment(logoutBtn, VPos.TOP);
        GridPane.setMargin(logoutBtn, new Insets(10, 10, 0, 0));
        wrapper.add(logoutBtn, 0, 0);

        // put everything into main menu box
        regisMenu.getChildren().addAll(courseNameLabel, descripLabel, descripTextLabel, regisWrapper);

        // center everything
        regisMenu.setAlignment(Pos.CENTER);
        centerTextColumn(secCol);
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
