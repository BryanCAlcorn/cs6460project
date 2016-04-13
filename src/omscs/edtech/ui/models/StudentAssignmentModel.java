package omscs.edtech.ui.models;

import javafx.beans.property.*;

public class StudentAssignmentModel {

    private Integer gradeId;
    private Integer classId;
    private Integer assignmentId;
    private StudentModel studentModel;
    private DoubleProperty studentGrade;
    private StringProperty assignmentText;
    private StringProperty assignmentFeedback;
    private BooleanProperty assignmentMissing;

    public StudentAssignmentModel(StudentModel studentModel, double grade) {
        studentGrade = new SimpleDoubleProperty(grade);
        this.studentModel = studentModel;
        assignmentText = new SimpleStringProperty();
        assignmentFeedback = new SimpleStringProperty();
        assignmentMissing = new SimpleBooleanProperty();
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public StudentModel getStudentModel() {
        return studentModel;
    }

    public Integer getStudentId(){
        return studentModel.getId();
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

    public boolean getAssignmentMissing() {
        return assignmentMissing.getValue();
    }

    public BooleanProperty assignmentMissingProperty() {
        return assignmentMissing;
    }

    public void setAssignmentMissing(boolean assignmentMissing) {
        this.assignmentMissing.setValue(assignmentMissing);
    }
}
