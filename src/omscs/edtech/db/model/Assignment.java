package omscs.edtech.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "Assignments")
public class Assignment {
    //@Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int maxPoints;
    private boolean useRubric;
    private String description;
    //@OneToMany
    private List<Rubric> rubrics;

    public Assignment(ResultSet rs) throws SQLException{
        id = rs.getInt("assignmentId");
        name = rs.getString("name");
        maxPoints = rs.getInt("maxPoints");
        description = rs.getString("description");
        useRubric = Boolean.parseBoolean(rs.getString("useRubric"));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
