package Boundary;

import Entity.Section;

import java.util.List;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

public class RegistrationForm {

    private Consumer<Section> onRegister;
    private RegistrationFormBuilder builder;
    private TableView<Section> sectionTable;

    public RegistrationForm() {
        builder = new RegistrationFormBuilder();
    }

    public Parent display(String title, String description) {
        sectionTable = builder.createSectionTable();
        // build the full form with table + description + register button
        Parent formRoot = builder.buildForm(title, description, sectionTable);
        formRoot.setUserData(this);
        return formRoot;
    }

    public void fillTable(List<Section> sections) {
        sectionTable.setItems(FXCollections.observableArrayList(sections));
    }

    public void triggerRegister(Section section) {
        if (onRegister != null) {
            onRegister.accept(section);
        }
    }

    public void setOnRegister(Consumer<Section> handler) {
        this.onRegister = handler;
    }

    // optional getters if you need to attach listeners later
    public RegistrationFormBuilder getBuilder() {
        return builder;
    }
}