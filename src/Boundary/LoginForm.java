package Boundary;

import Control.LoginControl;
import Control.StartController;
import Entity.Account;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginForm {
    
    private Label errorLabel;
    private LoginControl loginControl;
    private Stage primaryStage;
    private StartController startController;

    public LoginForm(LoginControl loginControl, Stage primaryStage, StartController startController) {
        this.loginControl = loginControl;
        this.primaryStage = primaryStage;
        this.startController = startController;
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
        Account account = loginControl.login(usn, pwd);

        if (account != null) {
            System.out.println("Successful login!");
            startController.onLoginSuccess(account);
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
}