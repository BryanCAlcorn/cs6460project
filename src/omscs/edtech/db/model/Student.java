package omscs.edtech.db.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//@Entity
//@Table(name = "Students")
public class Student {
    //@Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int classId;
    private String firstName;
    private String lastName;
    private String eMailAddress;
    private Map<Integer,Grade> grades;

    public Student(ResultSet rs) throws SQLException{
        id = rs.getInt("studentId");
        firstName = rs.getString("firstName");
        lastName = rs.getString("lastName");
        eMailAddress = rs.getString("eMailAddress");
        grades = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public Grade getGrade(int assignmentId){
        return grades.get(assignmentId);
    }

    public void addGrade(int assignmentId, Grade grade){
        grades.put(assignmentId, grade);
    }
}
