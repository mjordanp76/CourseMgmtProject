package Boundary;

import Entity.Section;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class RegistrationFormBuilder {

    private VBox rootVBox;
    private Label tableTitleLabel;
    private Label tableDescriptionLabel;
    private TableView<Section> tableView;

    public RegistrationFormBuilder() {
        rootVBox = new VBox(15);
        rootVBox.setAlignment(Pos.TOP_CENTER);

        tableTitleLabel = new Label();
        tableTitleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        tableDescriptionLabel = new Label();
        tableDescriptionLabel.setStyle("-fx-font-size: 14;");

        tableView = new TableView<>();
    }

    public VBox buildForm(String title, String description) {

        tableTitleLabel.setText(title);
        tableDescriptionLabel.setText(description);

        // --- Build table columns ---
        TableColumn<Section, String> colSection = new TableColumn<>("Section");
        TableColumn<Section, String> colTime = new TableColumn<>("Time");
        TableColumn<Section, String> colLocation = new TableColumn<>("Location");
        TableColumn<Section, Void> colRegister = new TableColumn<>("");

        // Equal widths for simplicity
        colSection.setPrefWidth(150);
        colTime.setPrefWidth(150);
        colLocation.setPrefWidth(150);
        colRegister.setPrefWidth(150);

        // Dummy "Register" buttons
        Callback<TableColumn<Section, Void>, TableCell<Section, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btn = new Button("Register");

            {
                btn.setMaxWidth(Double.MAX_VALUE);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };
        colRegister.setCellFactory(cellFactory);

        tableView.getColumns().setAll(colSection, colTime, colLocation, colRegister);

        rootVBox.getChildren().setAll(tableTitleLabel, tableDescriptionLabel, tableView);

        return rootVBox;
    }

    // Getters for potential future use
    public TableView<Section> getTableView() {
        return tableView;
    }

    public Label getTitleLabel() {
        return tableTitleLabel;
    }

    public Label getDescriptionLabel() {
        return tableDescriptionLabel;
    }
}
