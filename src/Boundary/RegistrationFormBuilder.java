package Boundary;

import Entity.Section;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class RegistrationFormBuilder {

    private VBox rootVBox;
    private Label tableTitleLabel;
    private Label descriptionLabel;
    private Label courseDescription;
    private TableView<Section> tableView;

    public RegistrationFormBuilder() {
        rootVBox = new VBox(15);
        rootVBox.setAlignment(Pos.TOP_CENTER);

        tableTitleLabel = new Label();
        tableTitleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        descriptionLabel = new Label();

        courseDescription = new Label();
    }

    public VBox buildForm(String title, String description, TableView<Section> table) {

        tableTitleLabel.setText(title);
        descriptionLabel.setText("Course Description");
        descriptionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox.setMargin(descriptionLabel, new Insets(20, 0, 0, 0));
        courseDescription.setText(description);
        courseDescription.setWrapText(true);
        courseDescription.setMaxWidth(700);
        courseDescription.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");

        rootVBox.getChildren().setAll(tableTitleLabel, descriptionLabel, courseDescription, table);

        return rootVBox;
    }

    public TableView<Section> createSectionTable() {
        TableView<Section> table = new TableView<>();
        table.setStyle("-fx-font-size: 16px;");
        table.setMaxWidth(820);

        TableColumn<Section, String> letterCol = new TableColumn<>("Section");
        letterCol.setCellValueFactory(new PropertyValueFactory<>("courseAndSection"));
        letterCol.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeCol.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationCol.setCellFactory(col -> {
            TableCell<Section, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<Section, Void> regCol = new TableColumn<>("");
        regCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Register");
            {
                btn.setPrefWidth(80);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }

                btn.setOnAction(e -> {
                    // trigger callback stored inside RegistrationForm
                    Section section = getTableView().getItems().get(getIndex());
                    RegistrationForm parent = (RegistrationForm) btn.getScene().getRoot().getUserData();
                    if (parent != null) {
                        parent.triggerRegister(section);
                    }
                });
                setGraphic(btn);
                setAlignment(Pos.CENTER);
            }
        });

        letterCol.setPrefWidth(149);
        timeCol.setPrefWidth(260);
        locationCol.setPrefWidth(260);
        regCol.setPrefWidth(149);

        table.getColumns().addAll(letterCol, timeCol, locationCol, regCol);
        return table;
    }

    // getters for potential future use
    public TableView<Section> getTableView() {
        return tableView;
    }

    public Label getTitleLabel() {
        return tableTitleLabel;
    }

    public Label getDescriptionLabel() {
        return courseDescription;
    }
}