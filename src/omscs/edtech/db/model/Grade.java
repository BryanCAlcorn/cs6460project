package omscs.edtech.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.ResultSet;
import java.sql.SQLException;

@DatabaseTable(tableName = "Grades")
public class Grade {

    public final static String STUDENT_COL = "studentId";
    public final static String ASSIGNMENT_COL = "assignmentId";
    public final static String CLASS_COL = "classId";

    @DatabaseField(generatedId = true, columnName = "gradeId")
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = STUDENT_COL, foreignAutoRefresh = true)
    private Student student;
    @DatabaseField(canBeNull = false, foreign = true, columnName = CLASS_COL, foreignAutoRefresh = true)
    private Class dbClass;
    @DatabaseField(canBeNull = false, foreign = true, columnName = ASSIGNMENT_COL, foreignAutoRefresh = true)
    private Assignment assignment;
    @DatabaseField
    private double score;
    @DatabaseField
    private boolean isMissing;
    @DatabaseField
    private String feedback;

    public Grade(){
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Class getDbClass() {
        return dbClass;
    }

    public void setDbClass(Class dbClass) {
        this.dbClass = dbClass;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isMissing() {
        return isMissing;
    }

    public void setMissing(boolean missing) {
        isMissing = missing;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
