package omscs.edtech.ui.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClassModel {

    private Integer id;
    private StringProperty className;
    private IntegerProperty classPeriod;
    private IntegerProperty classYear;
    private BooleanProperty active;
    private ObservableList<StudentModel> students;

    public ClassModel(){
        className = new SimpleStringProperty();
        classPeriod = new SimpleIntegerProperty();
        classYear = new SimpleIntegerProperty();
        active = new SimpleBooleanProperty();
        students = FXCollections.observableArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ObservableList<StudentModel> studentsProperty(){
        return students;
    }

    public void addStudent(StudentModel student){
        students.add(student);
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

    @Override
    public String toString(){
        return  String.format("%s %d - %d", getClassName(), getClassPeriod(), getClassYear());
    }
}
