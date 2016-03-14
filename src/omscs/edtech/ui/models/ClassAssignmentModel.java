package omscs.edtech.ui.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassAssignmentModel {
    private BooleanProperty isAssigned;
    private StringProperty className;

    public ClassAssignmentModel(String name, boolean assigned){
        isAssigned = new SimpleBooleanProperty(assigned);
        className = new SimpleStringProperty(name);
    }

    public boolean getIsAssigned() {
        return isAssigned.getValue();
    }

    public BooleanProperty isAssignedProperty() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned.setValue(isAssigned);
    }

    public String getClassName() {
        return className.getValue();
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.setValue(className);
    }

    @Override
    public String toString() {
        return getClassName();
    }
}
