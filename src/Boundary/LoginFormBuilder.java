package Boundary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginFormBuilder {

    public VBox build(LoginForm loginForm) {
        // title
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // username field centered with label
        TextField userNameField = new TextField();
        userNameField.setPromptText("Username");
        userNameField.setMaxWidth(170);

        Label userLabel = new Label("Username");
        userLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        // move the label to the left of the text field
        userLabel.setTranslateX(-130);  

        StackPane userPane = new StackPane();
        userPane.getChildren().addAll(userLabel, userNameField);

        // password field centered with label
        PasswordField pwField = new PasswordField();
        pwField.setPromptText("Password");
        pwField.setMaxWidth(170);

        Label pwLabel = new Label("Password");
        pwLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        pwLabel.setTranslateX(-130);

        StackPane pwPane = new StackPane();
        pwPane.getChildren().addAll(pwLabel, pwField);

        // error label
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        loginForm.setErrorLabel(errorLabel);

        // login button
        Button loginButton = new Button("Submit");
        loginButton.setOnAction(e ->
            loginForm.submit(userNameField.getText(), pwField.getText())
        );
        loginButton.disableProperty().bind(
            userNameField.textProperty().isEmpty().or(pwField.textProperty().isEmpty())
        );

        // VBox layout
        VBox fieldsBox = new VBox(10, userPane, pwPane, loginButton, errorLabel);
        fieldsBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, titleLabel, fieldsBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-border-color: gray; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: white;");

        return mainLayout;
    }
}
