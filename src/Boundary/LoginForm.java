package Boundary;

import Control.LoginControl;

import javafx.stage.Stage;

public class LoginForm {
    
    private LoginControl loginControl;
    private Stage stage;
    private Stage primaryStage;

    public LoginForm(LoginControl loginControl, Stage primaryStage) {
        this.loginControl = loginControl;
        this.primaryStage = primaryStage;
    }

    public void display() {
        // TODO: create UI
    }

    public void submit(String usn, String pwd) {
        //loginControl.login(usn, pwd);
    }

    public void close() {
        stage.close();
    }
}
