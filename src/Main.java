import Control.StartController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    // start method comes from Application in JavaFX
    public void start(Stage primaryStage) {
        StartController startController = new StartController(primaryStage);
        startController.initiate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
