package Boundary;

import Control.SectionController;
import Control.TeacherController;
import Entity.Course;
import Entity.Section;
import Entity.Section;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.util.Callback;

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
    }

    public TableView<Section> getSectionTable() {
        return sectionTable;
    }

    public VBox getDashboardTables() {
        return builder.buildDashboard(sectionTable);
    }

    private void setupSelectColumn() {
        TableColumn<Section, Void> selectCol = (TableColumn<Section, Void>) sectionTable.getColumns().get(2);

        selectCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Select");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    btn.setOnAction(e -> {
                        System.out.print("Clicked!");
                        Section Section = getTableView().getItems().get(getIndex());
                        teacherController.openRegistrationForm(Section);
                    });
                }
            }
        });
    }

    public void populateTables(List<Section> teacherSections) {
        sectionTable.setItems(FXCollections.observableArrayList(teacherSections));
        Platform.runLater(this::setupSelectColumn);
    }

    // Called by controller to refresh tables after registration
    public void updateTables(List<Section> teacherSections) {
        sectionTable.setItems(FXCollections.observableArrayList(teacherSections));
        Platform.runLater(this::setupSelectColumn);
    }
}
