package Boundary;

import Control.StartController;

// import javafx.application.Platform;
// import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginForm {

    private StartController controller;
    private VBox loginPane;

    public LoginForm(StartController controller) {
        this.controller = controller;
        //loginPane = new VBox();
    }

    public void show(Stage stage) {
        VBox loginPane = new VBox(10);
        loginPane.setPadding(new Insets(20));

        Label labelUserName = new Label("Username");
        TextField userNameField = new TextField();
        userNameField.setMaxWidth(175);

        Label labelPassword = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(175);

        Button loginButton = new Button("Submit");
        loginButton.setOnAction(e -> handleLogin(userNameField.getText(), passwordField.getText()));

        int labelWidth = 60;
        labelUserName.setPrefWidth(labelWidth);
        labelPassword.setPrefWidth(labelWidth);

        HBox userPane = new HBox(10);
        //userPane.setPadding(new Insets(20));
        userPane.getChildren().addAll(labelUserName, userNameField);
        HBox passwordPane = new HBox(10);
        //passwordPane.setPadding(new Insets(20));
        passwordPane.getChildren().addAll(labelPassword, passwordField);

        loginPane.getChildren().addAll(userPane, passwordPane, loginButton);

        Scene scene = new Scene(loginPane, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void handleLogin(String usn, String pwd) {
        // TODO: authenticate with DB
        System.out.println("Attempted login: " + usn);
    }
}