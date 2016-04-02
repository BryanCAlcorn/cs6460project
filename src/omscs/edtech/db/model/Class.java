package omscs.edtech.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Entity
//@Table(name = "Classes")
public class Class {
    //@Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int period;
    private int year;
    private boolean active;
    private Date startDate;
    private Date endDate;
    //@OneToMany
    private List<Student> students;
    private List<Assignment> assignments;

    public Class(ResultSet rs) throws SQLException{
        id = rs.getInt("classId");
        name = rs.getString("name");
        period = rs.getInt("period");
        year = rs.getInt("year");
        active = Boolean.parseBoolean(rs.getString("active"));
        students = new ArrayList<>();
        assignments = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
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
}
