package omscs.edtech.ui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

public class StudentAssignmentModel {

    private StudentModel studentModel;
    private DoubleProperty studentGrade;

    public StudentAssignmentModel(StudentModel studentModel, double grade) {
        studentGrade = new SimpleDoubleProperty(grade);
        this.studentModel = studentModel;
    }

    public Number getStudentGrade() {
        return studentGrade.getValue();
    }

    public DoubleProperty studentGradeProperty() {
        return studentGrade;
    }

    public void setStudentGrade(Number studentGrade) {
        this.studentGrade.setValue(studentGrade);
    }

    public StringProperty getStudentNameProperty(){
        return studentModel.getStudentNameProperty();
    }
}
