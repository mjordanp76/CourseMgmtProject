import Control.StartController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) { // start is a method from the Application class in JavaFX
        StartController controller = new StartController();
        controller.initiate(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
