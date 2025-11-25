package Boundary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainMenuBuilder {

    private BorderPane root;
    private Label nameLabel;
    private Label deptLabel;
    private Button logoutButton;
    private Label subtitleLabel;
    private VBox contentArea;

    public MainMenuBuilder() {
        root = new BorderPane();
        nameLabel = new Label();
        deptLabel = new Label();
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

    public Label getDeptLabel() {
        return deptLabel;
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

    public void setDept(String deptCode) {
        String majorName = getMajorFromDept(deptCode);
        deptLabel.setText(majorName);
    }


    // -----------------------------
    // BUILD DASHBOARD VIEW
    // -----------------------------
    public void buildDashboard(Node tables) {
        // Centered title + logout at top-right using StackPane
        Label title = nameLabel; // your existing label
        Label major = deptLabel;
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        major.setStyle("-fx-font-size: 18px;");

        VBox titleBox = new VBox(title, major);
        titleBox.setAlignment(Pos.CENTER);

        Button logout = logoutButton;
        logout.setVisible(true);

        StackPane headerPane = new StackPane();
        headerPane.setPadding(new Insets(10));
        headerPane.getChildren().addAll(titleBox, logout);

        // Center the title, and anchor logout to the top-right
        StackPane.setAlignment(titleBox, Pos.CENTER);
        StackPane.setAlignment(logout, Pos.TOP_RIGHT);
        // optional: give logout a small margin from the top-right edge
        StackPane.setMargin(logout, new Insets(0, 10, 0, 0));

        // Body area (unchanged)
        subtitleLabel.setVisible(false);
        contentArea.getChildren().setAll(tables);
        VBox body = new VBox(20, subtitleLabel, contentArea);
        body.setAlignment(Pos.TOP_CENTER);

        root.setTop(headerPane);
        root.setCenter(body);
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

    private String getMajorFromDept(String dept) {
    return switch (dept) {
        case "CSCI" -> "Computer Science";
        case "CYBR" -> "Cybersecurity";
        default -> "Unknown Major";
    };
}
}