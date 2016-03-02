package omscs.edtech.db.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "GradeSets")
public class GradeSet {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToOne
    private Assignment assignment;
    @OneToOne
    private Class aClass;
    @OneToMany
    private List<Grade> grades;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
