package Boundary;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenuBuilder {

    private BorderPane root;
    private Label nameLabel;
    private Button logoutButton;
    private Label subtitleLabel;
    private VBox contentArea;

    public MainMenuBuilder() {
        root = new BorderPane();
        nameLabel = new Label();
        logoutButton = new Button("Logout");
        subtitleLabel = new Label();
        contentArea = new VBox(15);
        contentArea.setAlignment(Pos.TOP_CENTER);
    }

    public BorderPane getRoot() {
        return root;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Label getSubtitleLabel() {
        return subtitleLabel;
    }

    public VBox getContentArea() {
        return contentArea;
    }

    // -----------------------------
    // BUILD DASHBOARD VIEW
    // -----------------------------
    public void buildDashboard(Node tables) {

        // Header bar: name centered, logout on right
        HBox leftSpacer = new HBox();
        HBox centerBox = new HBox(nameLabel);
        centerBox.setAlignment(Pos.CENTER);
        HBox rightBox = new HBox(logoutButton);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox(leftSpacer, centerBox, rightBox);
        header.setStyle("-fx-padding: 10;");

        // Hide subtitle
        subtitleLabel.setVisible(false);

        // Content area
        contentArea.getChildren().setAll(tables);
        VBox body = new VBox(20, subtitleLabel, contentArea);
        body.setAlignment(Pos.TOP_CENTER);

        root.setTop(header);
        root.setCenter(body);

        logoutButton.setVisible(true);
    }

    // -----------------------------
    // BUILD DETAIL VIEW
    // -----------------------------
    public void buildDetail(Node content, String subtitle) {

        // Header only has name
        HBox header = new HBox(nameLabel);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-padding: 10;");

        // Subtitle
        subtitleLabel.setText(subtitle);
        subtitleLabel.setVisible(true);

        // Content
        contentArea.getChildren().setAll(content);

        VBox body = new VBox(20, subtitleLabel, contentArea);
        body.setAlignment(Pos.TOP_CENTER);

        root.setTop(header);
        root.setCenter(body);

        logoutButton.setVisible(false);
    }
}