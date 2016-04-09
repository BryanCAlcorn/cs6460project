package omscs.edtech.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "Assignments")
public class Assignment {
    @DatabaseField(generatedId = true, columnName = "assignmentId")
    private Integer id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int maxPoints;
    @DatabaseField
    private boolean useRubric;
    @DatabaseField
    private String description;
    @DatabaseField(canBeNull = false, foreign = true)
    private Class dbClass;
    @ForeignCollectionField
    private ForeignCollection<Grade> grades;

    private List<Rubric> rubrics;

    public Assignment(){
    }

    public Integer getId() {
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
