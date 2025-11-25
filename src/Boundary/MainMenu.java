package Boundary;

import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {
    
    private MainMenuBuilder builder;
    private Stage stage;

    public MainMenu(MainMenuBuilder builder, Stage stage) {
        this.builder = builder;
        this.stage = stage;
    }

    public BorderPane getRoot() {
        return builder.getRoot();
    }

    public Stage getStage() {
        return stage;
    }

    public void setName(String name) {
        builder.getNameLabel().setText(name);
    }

    public void setDept(String dept) {
        builder.getDeptLabel().setText(dept);
    }

    public void showDashboardView(Node tables) {
        builder.buildDashboard(tables);
    }
    
    public void showDetailView(Node content, String subtitle) {
        builder.buildDetail(content, subtitle);
    }

    public Button getLogoutButton() {
        return builder.getLogoutButton();
    }
}