package omscs.edtech.db.interfaces;

import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Grade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GradeDataConnector {

    private static int getMaxId(){
        return SQLiteDBConnection.selectHighestId("Grades", "gradetId");
    }

    public static List<Grade> getGrades(int classId, int assignmentId){
        return SQLiteDBConnection.selectList(
                "SELECT * FROM Grades WHERE assignmentId = " + assignmentId +
                        " AND classId = " + classId,
                new GradeObjectFactory()
        );
    }

    public static Grade getGrade(int classId, int assignmentId, int studentId){
        return SQLiteDBConnection.selectSingle(
                "SELECT * FROM Grades WHERE assignmentId = " + assignmentId +
                        " AND classId = " + classId + " AND studentId = " + studentId,
                new GradeObjectFactory()
        );
    }

    private static class GradeObjectFactory implements DBObjectFactory<Grade>{
        @Override
        public Grade fromDb(ResultSet rs) throws SQLException {
            return new Grade(rs);
        }
    }
}
