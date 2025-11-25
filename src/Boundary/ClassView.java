package Boundary;

import Entity.GradeList;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClassView {

    private TableView<GradeList> table = new TableView<>();
    private Consumer<List<GradeList>> onSave;

    public Parent display(String courseName, String teacherName) {

        table.setMaxWidth(840);
        table.setStyle("-fx-font-size: 16px;");

        // table Columns
        TableColumn<GradeList, String> nameCol = new TableColumn<>("Student");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        nameCol.setEditable(false);
        nameCol.setPrefWidth(139);
        nameCol.setCellFactory(col -> {
            TableCell<GradeList, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<GradeList, Double> a1Col = new TableColumn<>("Assignment#1");
        a1Col.setCellValueFactory(new PropertyValueFactory<>("asgmt1"));
        a1Col.setPrefWidth(140);
        a1Col.setCellFactory(col -> {
            TextFieldTableCell<GradeList, Double> cell = new TextFieldTableCell<>(new DoubleStringConverter());
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        a1Col.setOnEditCommit(e -> e.getRowValue().setAsgmt1(e.getNewValue()));

        TableColumn<GradeList, Double> midCol = new TableColumn<>("Midterm");
        midCol.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        midCol.setPrefWidth(140);
        midCol.setCellFactory(col -> {
            TextFieldTableCell<GradeList, Double> cell = new TextFieldTableCell<>(new DoubleStringConverter());
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        midCol.setOnEditCommit(e -> e.getRowValue().setMidterm(e.getNewValue()));

        TableColumn<GradeList, Double> a2Col = new TableColumn<>("Assignment#2");
        a2Col.setCellValueFactory(new PropertyValueFactory<>("asgmt2"));
        a2Col.setPrefWidth(140);
        a2Col.setCellFactory(col -> {
            TextFieldTableCell<GradeList, Double> cell = new TextFieldTableCell<>(new DoubleStringConverter());
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        a2Col.setOnEditCommit(e -> e.getRowValue().setAsgmt2(e.getNewValue()));

        TableColumn<GradeList, Double> finalCol = new TableColumn<>("Final");
        finalCol.setCellValueFactory(new PropertyValueFactory<>("final"));
        finalCol.setPrefWidth(140);
        finalCol.setCellFactory(col -> {
            TextFieldTableCell<GradeList, Double> cell = new TextFieldTableCell<>(new DoubleStringConverter());
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        finalCol.setOnEditCommit(e -> e.getRowValue().setFinal(e.getNewValue()));

        TableColumn<GradeList, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(c ->
            new ReadOnlyObjectWrapper<>(c.getValue().getTotal())
        );
        totalCol.setEditable(false);
        totalCol.setPrefWidth(139);
        totalCol.setCellFactory(col -> {
            TableCell<GradeList, Double> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        table.getColumns().addAll(nameCol, a1Col, midCol, a2Col, finalCol, totalCol);
        table.setEditable(true);

        // header VBox for course & teacher names
        Label courseLabel = new Label(courseName);
        courseLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label teacherLabel = new Label(teacherName);
        teacherLabel.setStyle("-fx-font-size: 18px;");
        VBox headerBox = new VBox(5, courseLabel, teacherLabel);
        headerBox.setAlignment(Pos.CENTER);

        // save button aligned right
        Button saveBtn = new Button("Submit");
        saveBtn.setOnAction(e -> {
            if (onSave != null) onSave.accept(new ArrayList<>(table.getItems()));
        });

        HBox buttonBox = new HBox(saveBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // optional top padding

        // root VBox
        VBox root = new VBox(10, headerBox, table, buttonBox);
        root.setPadding(new Insets(10));

        return root;
    }

    public void fillTable(List<GradeList> rows) {
        table.setItems(FXCollections.observableArrayList(rows));
    }

    public void setOnSave(Consumer<List<GradeList>> handler) {
        this.onSave = handler;
    }
}

