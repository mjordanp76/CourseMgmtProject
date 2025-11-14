package Boundary;

import Control.StartController;
import javafx.geometry.HPos;
// import javafx.application.Platform;
// import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginForm {

    private StartController controller;
    private VBox loginPane;

    public LoginForm(StartController controller) {
        this.controller = controller;
    }

    public void show(Stage stage) {
        // grid pane holds the login components (labels, fields, button)
        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(0));
        loginPane.setHgap(10);
        loginPane.setVgap(10);

        // border
        loginPane.setStyle(
            "-fx-border-color: gray;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;" +
            "-fx-background-color: white;" +
            "-fx-padding: 10;"
        );

        // Make grid 3x3
        // Left column - 20% width
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(28);
        // Center column - 60% width
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(44);
        // Right column - 20% width
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(28);

        // Add constraints to the grid
        loginPane.getColumnConstraints().addAll(col1, col2, col3);

        // "Login" label
        Label titleLabel = new Label("Login");
        titleLabel.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );

        // textfield labels
        Label labelUserName = new Label("Username");
        Label labelPassword = new Label("Password");

        // textfields
        TextField userNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        userNameField.setMaxWidth(190);
        passwordField.setMaxWidth(190);

         // box to hold both labels
        VBox fieldLabels = new VBox();
        fieldLabels.setSpacing(25);
        fieldLabels.getChildren().addAll(labelUserName, labelPassword);
        fieldLabels.setAlignment(Pos.CENTER_RIGHT);

        // box to hold both input fields
        VBox inputFields = new VBox();
        inputFields.setSpacing(20);
        inputFields.setPadding(new Insets(15, 0, 15, 0));
        inputFields.getChildren().addAll(userNameField, passwordField);
        inputFields.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Submit");
        loginButton.setOnAction(e -> handleLogin(userNameField.getText(), passwordField.getText()));

        // Add items to grid
        // (col#, row#)
        loginPane.add(titleLabel, 1, 0);
        loginPane.add(fieldLabels, 0, 1);
        loginPane.add(inputFields, 1, 1);
        loginPane.add(loginButton, 1, 2);

        // center title and button
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        
        // set preferred width/height of grid pane
        //loginPane.setPrefSize(400, Region.USE_COMPUTED_SIZE);
        loginPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // wrapper around grid pane
        // positions login "window" within main window
        StackPane loginStack = new StackPane(loginPane);
        StackPane.setAlignment(loginPane, Pos.CENTER);

        // get computer display size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        // compute __% of screen size
        double width = screenBounds.getWidth() * 0.8;
        double height = screenBounds.getHeight() * 0.8;

        // the login "scene" for the main window
        // scene will change after login to either student/teacher menus
        Scene scene = new Scene(loginStack, width, height);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void handleLogin(String usn, String pwd) {
        // TODO: authenticate with DB
        System.out.println("Attempted login: " + usn);
    }
}