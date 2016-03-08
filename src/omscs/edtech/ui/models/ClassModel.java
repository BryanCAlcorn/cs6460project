package omscs.edtech.ui.models;

import javafx.beans.property.*;

public class ClassModel {

    private StringProperty className;
    private IntegerProperty classPeriod;
    private IntegerProperty classYear;
    private BooleanProperty active;

    public ClassModel(){
        className = new SimpleStringProperty();
        classPeriod = new SimpleIntegerProperty();
        classYear = new SimpleIntegerProperty();
        active = new SimpleBooleanProperty();
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

    public int getClassPeriod() {
        return classPeriod.getValue();
    }

    public IntegerProperty classPeriodProperty() {
        return classPeriod;
    }

    public void setClassPeriod(int classPeriod) {
        this.classPeriod.setValue(classPeriod);
    }

    public int getClassYear() {
        return classYear.getValue();
    }

    public IntegerProperty classYearProperty() {
        return classYear;
    }

    public void setClassYear(int classYear) {
        this.classYear.setValue(classYear);
    }

    public boolean getActive() {
        return active.getValue();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.setValue(active);
    }
}
