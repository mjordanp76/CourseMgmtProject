package Boundary;

import Control.LoginControl;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginForm {
    
    private Label errorLabel;
    private LoginControl loginControl;
    private Stage primaryStage;

    public LoginForm(LoginControl loginControl, Stage primaryStage) {
        this.loginControl = loginControl;
        this.primaryStage = primaryStage;
    }

    public void display() {
        LoginFormBuilder builder = new LoginFormBuilder(); // GUI design in LoginFormBuilder
        VBox loginLayout = builder.build(this);

        Scene scene = new Scene(loginLayout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dejocato Course Management");
        primaryStage.show();
    }

    // submit button action
    public void submit(String usn, String pwd) {
        boolean success = loginControl.login(usn, pwd);

        if (success) {
            System.out.println("Successful login!");
            primaryStage.close(); // close login form after successful login
        } else {
        System.out.println("Attempted login from: " + usn);
        }
    }

    // display red error messages in login form
    public void displayError(String message) {
        Platform.runLater(() -> {
            if (errorLabel != null) {
                errorLabel.setText(message);
                errorLabel.setVisible(true);
            }
        });
    }

    public void setErrorLabel(Label label) {
        this.errorLabel = label; // used by loginFormBuilder to inject errorLabel
    }

    public void close() {
        primaryStage.close();
    }
}
