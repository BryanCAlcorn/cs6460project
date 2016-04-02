package omscs.edtech.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;

//@Entity
//@Table(name = "Grades")
public class Grade {
    //@Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int studentId;
    private int score;
    private boolean isMissing;
    private String feedback;

    public Grade(ResultSet rs) throws SQLException{
        id = rs.getInt("gradetId");
        studentId = rs.getInt("studentId");
        score = rs.getInt("score");
        isMissing = Boolean.parseBoolean(rs.getString("isMissing"));
        feedback = rs.getString("feedback");
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
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
