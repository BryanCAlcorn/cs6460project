package omscs.edtech.db.interfaces;

import javafx.scene.Parent;
import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

public class StudentDataConnector
{

    private static int getMaxId(){
        return SQLiteDBConnection.selectHighestId("Students", "studentId");
    }

    public static boolean saveStudents(List<Student> students){
        boolean saveSuccessful = true;
        if(students != null) {

            int maxId = getMaxId();
            for (Student student : students) {
                String query = "";
                if(student.getId() == -1){ //New student
                    maxId++; //Increment max ID
                    query = String.format("INSERT INTO Students" +
                            "(studentId, firstName, lastName, eMailAddress)" +
                            "VALUES (%d, %s, %s, %s)",
                            maxId, student.getFirstName(),
                            student.getLastName(), student.geteMailAddress());
                }else{ //Old student
                    query = String.format("UPDATE Students" +
                            "SET firstName=%s, lastName=%s,eMailAddress=%s" +
                            "WHERE studentId=%d",
                            student.getFirstName(), student.getLastName(),
                            student.geteMailAddress(), student.getId());
                }
                saveSuccessful &= SQLiteDBConnection.executeSQL(query);
            }
        }
        return saveSuccessful;
    }

    public static List<Student> getStudentsByClass(int classId){
        return SQLiteDBConnection.selectList(
                "SELECT * FROM Students LEFT JOIN StudentClasses " +
                        "ON Students.studentId = StudentClasses.studentId " +
                        "WHERE StudentClasses.classId = "+ classId,
                new StudentObjectFactory());
    }

    private static class StudentObjectFactory implements DBObjectFactory<Student>{
        @Override
        public Student fromDb(ResultSet rs) throws SQLException {
            return new Student(rs);
        }
    }
}
