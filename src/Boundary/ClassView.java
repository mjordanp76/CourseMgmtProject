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

        // --- Table Columns (unchanged) ---
        TableColumn<GradeList, String> nameCol = new TableColumn<>("Student");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        nameCol.setEditable(false);
        nameCol.setCellFactory(col -> {
            TableCell<GradeList, String> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<GradeList, Double> a1Col = new TableColumn<>("Assignment#1");
        a1Col.setCellValueFactory(new PropertyValueFactory<>("asgmt1"));
        a1Col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        a1Col.setOnEditCommit(e -> e.getRowValue().setAsgmt1(e.getNewValue()));
        a1Col.setCellFactory(col -> {
            TableCell<GradeList, Double> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<GradeList, Double> midCol = new TableColumn<>("Midterm");
        midCol.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        midCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        midCol.setOnEditCommit(e -> e.getRowValue().setMidterm(e.getNewValue()));
        midCol.setCellFactory(col -> {
            TableCell<GradeList, Double> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<GradeList, Double> a2Col = new TableColumn<>("Assignment#2");
        a2Col.setCellValueFactory(new PropertyValueFactory<>("asgmt2"));
        a2Col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        a2Col.setOnEditCommit(e -> e.getRowValue().setAsgmt2(e.getNewValue()));
        a2Col.setCellFactory(col -> {
            TableCell<GradeList, Double> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<GradeList, Double> finalCol = new TableColumn<>("Final");
        finalCol.setCellValueFactory(new PropertyValueFactory<>("final"));
        finalCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        finalCol.setOnEditCommit(e -> e.getRowValue().setFinal(e.getNewValue()));
        finalCol.setCellFactory(col -> {
            TableCell<GradeList, Double> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        TableColumn<GradeList, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(c ->
            new ReadOnlyObjectWrapper<>(c.getValue().getTotal())
        );
        totalCol.setEditable(false);
        totalCol.setCellFactory(col -> {
            TableCell<GradeList, Double> cell = new TextFieldTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        table.getColumns().addAll(nameCol, a1Col, midCol, a2Col, finalCol, totalCol);
        table.setEditable(true);

        // --- Header VBox for course & teacher names ---
        Label courseLabel = new Label("Course: " + courseName);
        Label teacherLabel = new Label("Teacher: " + teacherName);
        VBox headerBox = new VBox(5, courseLabel, teacherLabel);
        headerBox.setAlignment(Pos.CENTER);

        // --- Save button aligned right ---
        Button saveBtn = new Button("Submit");
        saveBtn.setOnAction(e -> {
            if (onSave != null) onSave.accept(new ArrayList<>(table.getItems()));
        });

        HBox buttonBox = new HBox(saveBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // optional top padding

        // --- Root VBox ---
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

