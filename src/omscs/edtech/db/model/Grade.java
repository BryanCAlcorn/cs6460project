package omscs.edtech.db.model;

import javax.persistence.*;
import java.util.UUID;

//@Entity
//@Table(name = "Grades")
public class Grade {
    //@Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int score;
    private boolean isMissing;
    private String feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
