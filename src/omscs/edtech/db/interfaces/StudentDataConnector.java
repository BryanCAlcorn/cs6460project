package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Student;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Student getStudentByName(Integer classId, String firstName, String lastName){
        try {
            studentDao = connection.getDao();

            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put(Student.CLASS_ID, classId);
            keyMap.put(Student.FIRST_NAME, firstName);
            keyMap.put(Student.LAST_NAME, lastName);

            List<Student> students = studentDao.queryForFieldValues(keyMap);
            connection.destroyConnection();
            if(students != null && !students.isEmpty()) {
                return students.get(0);
            }else {
                return null;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
