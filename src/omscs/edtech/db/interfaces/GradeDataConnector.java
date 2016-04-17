package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.query.In;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Grade;
import omscs.edtech.db.model.Student;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeDataConnector {


    SQLiteDBConnection connection;
    Dao<Grade, Integer> gradesDao;

    public GradeDataConnector(){
        connection = new SQLiteDBConnection(Grade.class);
    }

    public Grade getGrade(Integer classId, Integer assignmentId, Integer studentId){
        try {
            gradesDao = connection.getDao();

            Map<String, Object> keyMap = new HashMap<>();

            keyMap.put(Grade.ASSIGNMENT_COL, assignmentId);
            keyMap.put(Grade.CLASS_COL, classId);
            keyMap.put(Grade.STUDENT_COL, studentId);

            List<Grade> grades = gradesDao.queryForFieldValues(keyMap);
            connection.destroyConnection();
            if(grades != null && !grades.isEmpty()) {
                return grades.get(0);
            }else {
                return null;
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Grade> getGrades(Class dbClass, Assignment assignment){
        try {
            gradesDao = connection.getDao();

            Map<String, Object> keyMap = new HashMap<>();

            keyMap.put(Grade.ASSIGNMENT_COL, assignment.getId());
            keyMap.put(Grade.CLASS_COL, dbClass.getId());

            List<Grade> grades = gradesDao.queryForFieldValues(keyMap);
            connection.destroyConnection();
            return grades;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean saveGrade(Grade grade){
        return connection.basicSave(grade);
    }

    public boolean deleteGradesForAssignment(Assignment assignment){
        boolean deleteSuccessful = true;
        try {
            gradesDao = connection.getDao();

            DeleteBuilder<Grade, Integer> deleteBuilder = gradesDao.deleteBuilder();
            deleteBuilder.where().eq(Grade.ASSIGNMENT_COL, assignment.getId());
            deleteSuccessful = deleteBuilder.delete() >= 1;
            connection.destroyConnection();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            deleteSuccessful = false;
        }

        return deleteSuccessful;
    }
}
