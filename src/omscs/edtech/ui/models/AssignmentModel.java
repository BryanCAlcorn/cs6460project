package omscs.edtech.ui.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AssignmentModel {

    private Integer id;
    private StringProperty name;
    private ObservableList<ClassAssignmentModel> assignedClasses;
    private IntegerProperty maxPoints;
    private StringProperty description;

    public AssignmentModel(){
        name = new SimpleStringProperty();
        maxPoints = new SimpleIntegerProperty();
        description = new SimpleStringProperty();
        assignedClasses = FXCollections.observableArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ObservableList<ClassAssignmentModel> getAssignedClasses(){
        return assignedClasses;
    }

    public boolean hasAssignedClass(ClassAssignmentModel classAssignmentModel){
        return assignedClasses.contains(classAssignmentModel);
    }

    public void assignToClass(ClassAssignmentModel classAssignmentModel){
        if(classAssignmentModel != null) {
            assignedClasses.add(classAssignmentModel);
        }
    }

    public void unassignFromClass(ClassAssignmentModel classAssignmentModel){
        if(classAssignmentModel != null) {
            assignedClasses.remove(classAssignmentModel);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssignmentModel that = (AssignmentModel) o;

        if (!name.getValue().equals(that.name.getValue())) return false;
        if (!maxPoints.getValue().equals(that.maxPoints.getValue())) return false;
        return description != null ? description.getValue().equals(that.description.getValue()) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = name.getValue().hashCode();
        result = 31 * result + maxPoints.getValue().hashCode();
        result = 31 * result + (description != null ? description.getValue().hashCode() : 0);
        return result;
    }
}
