package Boundary;

import Entity.Course;
import Entity.GradeList;
import Entity.Section;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

        //tableView = new TableView<>();
    }

    public VBox buildForm(String title, String description, TableView<Section> table) {
        // rootVBox.getChildren().addAll(table);

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

        rootVBox.getChildren().setAll(tableTitleLabel, tableDescriptionLabel, table);

        return rootVBox;
    }

    public TableView<Section> createSectionTable() {
        TableView<Section> table = new TableView<>();

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

        letterCol.setPrefWidth(150);
        timeCol.setPrefWidth(250);
        locationCol.setPrefWidth(250);
        regCol.setPrefWidth(150);

        table.getColumns().addAll(letterCol, timeCol, locationCol, regCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
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