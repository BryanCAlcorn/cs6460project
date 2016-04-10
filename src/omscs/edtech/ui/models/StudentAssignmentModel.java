package omscs.edtech.ui.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentAssignmentModel {

    private Integer gradeId;
    private StudentModel studentModel;
    private DoubleProperty studentGrade;
    private StringProperty assignmentText;
    private StringProperty assignmentFeedback;

    public StudentAssignmentModel(StudentModel studentModel, double grade) {
        studentGrade = new SimpleDoubleProperty(grade);
        this.studentModel = studentModel;
        assignmentText = new SimpleStringProperty();
        assignmentFeedback = new SimpleStringProperty();
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
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

    public String getAssignmentText() {
        return assignmentText.getValue();
    }

    public StringProperty assignmentTextProperty() {
        return assignmentText;
    }

    public void setAssignmentText(String assignmentText) {
        this.assignmentText.setValue(assignmentText);
    }

    public String getAssignmentFeedback() {
        return assignmentFeedback.getValue();
    }

    public StringProperty assignmentFeedbackProperty() {
        return assignmentFeedback;
    }

    public void setAssignmentFeedback(String assignmentFeedback) {
        this.assignmentFeedback.setValue(assignmentFeedback);
    }
}
