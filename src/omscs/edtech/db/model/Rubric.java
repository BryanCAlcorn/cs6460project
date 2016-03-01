package omscs.edtech.db.model;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "Rubrics")
public class Rubric {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int pointValue;
    private String key;
    private String description;
    @OneToOne
    private Assignment assignment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
