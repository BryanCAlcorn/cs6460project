package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class StudentDataConnector
{
    SQLiteDBConnection connection;
    private Dao<Student, Integer> studentDao;

    public StudentDataConnector(){
        connection = new SQLiteDBConnection(Student.class);
    }

    public boolean saveStudents(List<Student> students){
        boolean success = true;
        try {
            studentDao = connection.getDao();
            for (Student student : students) {
                studentDao.createOrUpdate(student);
            }
            connection.destroyConnection();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            success = false;
        }
        return success;
    }

    public List<Student> getStudentsByClass(Class dbClass){
        try {
            studentDao = connection.getDao();
            List<Student> classes = studentDao.queryForEq(Student.CLASS_ID, dbClass.getId());
            connection.destroyConnection();
            return classes;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
