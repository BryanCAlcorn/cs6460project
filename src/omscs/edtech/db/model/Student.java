package omscs.edtech.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@DatabaseTable(tableName = "Students")
public class Student {

    public final static String CLASS_ID = "dbClass_id";
    public final static String FIRST_NAME = "firstName";
    public final static String LAST_NAME = "lastName";

    @DatabaseField(generatedId = true, columnName = "studentId")
    private Integer id;
    @DatabaseField(columnName = FIRST_NAME)
    private String firstName;
    @DatabaseField(columnName = LAST_NAME)
    private String lastName;
    @DatabaseField
    private String eMailAddress;
    @DatabaseField(canBeNull = false, foreign = true, columnName = CLASS_ID)
    private Class dbClass;
    @ForeignCollectionField
    private ForeignCollection<Grade> grades;

    public Student(Integer studentId){
        this.id = studentId;
    }

    public Student(){

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMailAddress() {
        return eMailAddress;
    }

    public void seteMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }

    public ForeignCollection<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ForeignCollection<Grade> grades) {
        this.grades = grades;
    }

    public Class getDbClass() {
        return dbClass;
    }

    public void setDbClass(Class dbClass) {
        this.dbClass = dbClass;
    }

    //    public Grade getGrade(int assignmentId){
//        return grades.get(assignmentId);
//    }
//
//    public void addGrade(int assignmentId, Grade grade){
//        grades.put(assignmentId, grade);
//    }
}
