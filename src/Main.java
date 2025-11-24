import org.sqlite.core.DB;

import Control.DBConnector;
import Control.StartController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    // start method comes from Application in JavaFX
    public void start(Stage primaryStage) {
        // create ONE db that all controllers will share
        DBConnector db = new DBConnector();

        // pass db to StartController
        StartController startController = new StartController(primaryStage, db);

        // start the program
        startController.initiate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
