package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Grade;

import java.sql.ResultSet;
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
        boolean saveSuccessful = true;
        try {
            gradesDao = connection.getDao();
            Dao.CreateOrUpdateStatus status = gradesDao.createOrUpdate(grade);
            connection.destroyConnection();
            saveSuccessful = status.isCreated() || status.isUpdated();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            saveSuccessful = false;
        }
        return saveSuccessful;
    }

//    private static int getMaxId(){
//        return SQLiteDBConnection.selectHighestId("Grades", "gradetId");
//    }
//
//    public static List<Grade> getGrades(int classId, int assignmentId){
//        return SQLiteDBConnection.selectList(
//                "SELECT * FROM Grades WHERE assignmentId = " + assignmentId +
//                        " AND classId = " + classId,
//                new GradeObjectFactory()
//        );
//    }
//
//    public static Grade getGrade(int classId, int assignmentId, int studentId){
//        return SQLiteDBConnection.selectSingle(
//                "SELECT * FROM Grades WHERE assignmentId = " + assignmentId +
//                        " AND classId = " + classId + " AND studentId = " + studentId,
//                new GradeObjectFactory()
//        );
//    }
//
//    private static class GradeObjectFactory implements DBObjectFactory<Grade>{
//        @Override
//        public Grade fromDb(ResultSet rs) throws SQLException {
//            return new Grade(rs);
//        }
//    }
}
