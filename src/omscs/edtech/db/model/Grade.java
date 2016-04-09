package omscs.edtech.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.ResultSet;
import java.sql.SQLException;

@DatabaseTable(tableName = "Grades")
public class Grade {
    @DatabaseField(generatedId = true, columnName = "gradeId")
    private Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    private Student student;
    @DatabaseField(canBeNull = false, foreign = true)
    private Assignment assignment;
    @DatabaseField
    private int score;
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

    public Assignment getAssignment() {
        return assignment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
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
