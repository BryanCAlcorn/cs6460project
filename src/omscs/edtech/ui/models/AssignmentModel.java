package omscs.edtech.ui.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssignmentModel {

    private StringProperty name;
    private ObservableList<ClassModel> assignedClasses;
    private IntegerProperty maxPoints;
    private StringProperty description;

    public AssignmentModel(){
        name = new SimpleStringProperty();
        maxPoints = new SimpleIntegerProperty();
        description = new SimpleStringProperty();
        assignedClasses = FXCollections.observableArrayList();
    }

    public ObservableList<ClassModel> getAssignedClasses(){
        return assignedClasses;
    }

    public String getName() {
        return name.getValue();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public int getMaxPoints() {
        return maxPoints.getValue();
    }

    public IntegerProperty maxPointsProperty() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints.setValue(maxPoints);
    }

    public String getDescription() {
        return description.getValue();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    @Override
    public String toString() {
        return getName();
    }
}
