package Boundary;

import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class MainMenu {
    
    private MainMenuBuilder builder;

    public MainMenu(MainMenuBuilder builder) {
        this.builder = builder;
    }

    public BorderPane getRoot() {
        return builder.getRoot();
    }

    public void setName(String name) {
        builder.getNameLabel().setText(name);
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
