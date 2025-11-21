package Boundary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginFormBuilder {
    
    public VBox build(LoginForm loginForm) {
        // title
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // username and password fields
        TextField userNameField = new TextField();
        PasswordField pwField = new PasswordField();

        userNameField.setPromptText("Username");
        pwField.setPromptText("Password");
        userNameField.setMaxWidth(190);
        pwField.setMaxWidth(190);

        // error label (hidden by default)
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        loginForm.setErrorLabel(errorLabel);

        // login button
        Button loginButton = new Button("Submit");
        loginButton.setOnAction(e ->
            loginForm.submit(userNameField.getText(), pwField.getText())
        );

        // disable button until fields are non-empty
        loginButton.disableProperty().bind(
            userNameField.textProperty().isEmpty().or(pwField.textProperty().isEmpty())
        );

        // form layout
        VBox fieldsBox = new VBox(10, userNameField, pwField, loginButton, errorLabel);
        fieldsBox.setAlignment(Pos.CENTER);

        // main container
        VBox mainLayout = new VBox(20, titleLabel, fieldsBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-border-color: gray; " +
                            "-fx-border-width: 2; " +
                            "-fx-border-radius: 5; " +
                            "-fx-background-color: white;"
        );
                            
        return mainLayout;
    }
}
