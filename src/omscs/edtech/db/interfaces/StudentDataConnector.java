package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
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

    public boolean deleteStudentsByClass(Class dbClass){
        try {
            studentDao = connection.getDao();

            DeleteBuilder<Student, Integer> deleteBuilder = studentDao.deleteBuilder();
            deleteBuilder.where().eq(Student.CLASS_ID, dbClass.getId());
            int result = deleteBuilder.delete();
            connection.destroyConnection();
            return result >= 1;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Student getStudentByName(Integer classId, String firstName, String lastName){
        try {
            studentDao = connection.getDao();

            QueryBuilder<Student, Integer> queryBuilder = studentDao.queryBuilder();

            queryBuilder.where()
                    .eq(Student.CLASS_ID, classId)
                    .and()
                    .like(Student.FIRST_NAME, firstName)
                    .and()
                    .like(Student.LAST_NAME, lastName);

            List<Student> students = queryBuilder.query();
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
