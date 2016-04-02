package omscs.edtech.db.interfaces;

import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDataConnector
{

    public static int getMaxId(){
        return SQLiteDBConnection.selectHighestId("Students", "studentId");
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
