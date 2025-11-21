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
    private Stage stage;

    public LoginForm(LoginControl loginControl, Stage primaryStage) {
        this.loginControl = loginControl;
        this.primaryStage = primaryStage;
    }

    public void display() {
        LoginFormBuilder builder = new LoginFormBuilder();
        VBox loginLayout = builder.build(this);

        Scene scene = new Scene(loginLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public void submit(String usn, String pwd) {
        boolean success = loginControl.login(usn, pwd);

        if (success) {
            System.out.println("Successful login!");
            close(); // close login form after successful login
        } else {
            displayError("Invalid username or password.");
        }
        System.out.println("Attempted login from: " + usn);
    }

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
        stage.close();
    }
}
