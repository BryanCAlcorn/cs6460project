package omscs.edtech.db.model;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "Grades")
public class Grade {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int score;
    private boolean isMissing;
    private String feedback;
    @OneToOne
    private Student student;
    @OneToOne
    private GradeSet gradeSet;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public GradeSet getGradeSet() {
        return gradeSet;
    }

    public void setGradeSet(GradeSet gradeSet) {
        this.gradeSet = gradeSet;
    }
}
