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

    public final static String ID_COLUMN = "assignmentId";

    @DatabaseField(generatedId = true, columnName = ID_COLUMN)
    private Integer id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int maxPoints;
    @DatabaseField
    private boolean useRubric;
    @DatabaseField
    private String description;
    @ForeignCollectionField
    private ForeignCollection<Grade> grades;

    private List<Class> dbClasses;
    private List<Rubric> rubrics;

    public Assignment(){
        dbClasses = new ArrayList<>();
    }

    public Assignment(Integer assignmentId){
        id = assignmentId;
        dbClasses = new ArrayList<>();
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

    public List<Class> getDbClasses() {
        return dbClasses;
    }

    public void addDbClass(Class dbClass) {
        if(dbClass != null) {
            this.dbClasses.add(dbClass);
        }
    }

    public ForeignCollection<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ForeignCollection<Grade> grades) {
        this.grades = grades;
    }
}
