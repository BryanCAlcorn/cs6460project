package omscs.edtech.ui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class StudentAssignmentModel {

    private StudentModel studentModel;
    private DoubleProperty studentGrade;

    public StudentAssignmentModel(StudentModel studentModel, double grade) {
        studentGrade = new SimpleDoubleProperty(grade);
        this.studentModel = studentModel;
    }

    public double getStudentGrade() {
        return studentGrade.getValue();
    }

    public DoubleProperty studentGradeProperty() {
        return studentGrade;
    }

    public void setStudentGrade(double studentGrade) {
        this.studentGrade.setValue(studentGrade);
    }
}
