package omscs.edtech.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentModel {
    private Integer id;
    private StringProperty studentName;
    private StringProperty studentEmail;

    public StudentModel(String name, String eMail){
        studentName = new SimpleStringProperty(name);
        studentEmail = new SimpleStringProperty(eMail);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public final String getStudentName(){
        return studentName.getValue();
    }

    public final void setStudentName(String studentName){
        this.studentName.setValue(studentName);
    }

    public StringProperty getStudentNameProperty(){
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail.getValue();
    }

    public StringProperty studentEmailProperty() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail.setValue(studentEmail);
    }
}
