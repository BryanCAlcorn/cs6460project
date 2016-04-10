package omscs.edtech.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ClassAssignments")
public class ClassAssignment {

    public final static String CLASS_COLUMN = "class_id";
    public final static String ASSIGNMENT_COLUMN = "assignment_id";

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(foreign = true, columnName = CLASS_COLUMN)
    private Class dbClass;
    @DatabaseField(foreign = true, columnName = ASSIGNMENT_COLUMN)
    private Assignment assignment;

    public ClassAssignment(){

    }

    public ClassAssignment(Class dbClass, Assignment assignment){
        this.dbClass = dbClass;
        this.assignment = assignment;
    }

    public Integer getId() {
        return id;
    }

    public Class getDbClass() {
        return dbClass;
    }

    public void setDbClass(Class dbClass) {
        this.dbClass = dbClass;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassAssignment that = (ClassAssignment) o;

        if (!dbClass.getId().equals(that.dbClass.getId())) return false;
        return assignment.getId().equals(that.assignment.getId());
    }

    @Override
    public int hashCode() {
        int result = dbClass.getId().hashCode();
        result = 31 * result + assignment.getId().hashCode();
        return result;
    }
}
