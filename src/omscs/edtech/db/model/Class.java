package omscs.edtech.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.LazyForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@DatabaseTable(tableName = "Classes")
public class Class {

    public final static String ID_COLUMN = "classId";

    @DatabaseField(generatedId = true, columnName = ID_COLUMN)
    private Integer id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int period;
    @DatabaseField
    private int year;
    @DatabaseField(index = true)
    private boolean active;
    @DatabaseField
    private Date startDate;
    @DatabaseField
    private Date endDate;
    @ForeignCollectionField
    private ForeignCollection<Student> students;

    private List<Assignment> assignments;

    public Class(Integer classId){
        this.id = classId;
    }

    public Class(){
    }

    public ForeignCollection<Student> getStudents() {
        return students;
    }

    public void setStudents(ForeignCollection<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
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

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return  String.format("%s %d - %d", getName(), getPeriod(), getYear());
    }
}
