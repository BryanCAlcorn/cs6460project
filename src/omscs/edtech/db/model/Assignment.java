package omscs.edtech.db.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Assignments")
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @OneToMany
    private List<Class> assignedClasses;
    private int maxPoints;
    private boolean useRubric;
    private String description;
    @OneToMany
    private List<Rubric> rubrics;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Class> getAssignedClasses() {
        return assignedClasses;
    }

    public void setAssignedClasses(List<Class> assignedClasses) {
        this.assignedClasses = assignedClasses;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public boolean isUseRubric() {
        return useRubric;
    }

    public void setUseRubric(boolean useRubric) {
        this.useRubric = useRubric;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Rubric> getRubrics() {
        return rubrics;
    }

    public void setRubrics(List<Rubric> rubrics) {
        this.rubrics = rubrics;
    }
}
