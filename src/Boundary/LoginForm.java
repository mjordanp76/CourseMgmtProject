package Boundary;

import Control.StartController;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

public class LoginForm {

    private StartController controller;
    private VBox loginPane;

    public LoginForm(StartController controller) {
        this.controller = controller;
        loginPane = new VBox();
    }

    public void show(Stage stage) {
        Scene scene = new Scene(loginPane, 800, 300);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}