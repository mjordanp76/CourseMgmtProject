package Boundary;

import Control.TeacherController;
import Entity.Section;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;

public class TeacherMenu {

    private MainMenu mainMenu;
    private TeacherMenuBuilder builder;
    private TableView<Section> sectionTable;
    private TeacherController teacherController;

    public TeacherMenu(MainMenu mainMenu, TeacherController teacherController) {
        this.mainMenu = mainMenu;
        this.teacherController = teacherController;
        builder = new TeacherMenuBuilder();
        sectionTable = builder.createSectionTable();

        // ensure select column is set up right away (builder should create at least 3 cols)
        setupSelectColumn();
    }

    public TableView<Section> getSectionTable() {
        return sectionTable;
    }

    public VBox getDashboardTables() {
        return builder.buildDashboard(sectionTable);
    }

    private void setupSelectColumn() {
        TableColumn<Section, Void> selectCol = (TableColumn<Section, Void>) sectionTable.getColumns().get(2);

        selectCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Section, Void> call(TableColumn<Section, Void> col) {
                return new TableCell<>() {
                    private final Button btn = new Button("Select");

                    {
                        btn.setOnAction(e -> {
                            int idx = getIndex();
                            if (idx >= 0 && idx < getTableView().getItems().size()) {
                                Section section = getTableView().getItems().get(idx);
                                // call the correct controller method
                                teacherController.openClassView(section);
                            }
                        });
                        setAlignment(Pos.CENTER);
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
            }
        });
    }

    public void populateTables(List<Section> teacherSections) {
        sectionTable.setItems(FXCollections.observableArrayList(teacherSections));
        Platform.runLater(this::setupSelectColumn);
    }

    // Called by controller to refresh tables after registration/grade save
    public void updateTables(List<Section> teacherSections) {
        sectionTable.setItems(FXCollections.observableArrayList(teacherSections));
        Platform.runLater(this::setupSelectColumn);
    }
}
