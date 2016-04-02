package omscs.edtech.db.interfaces;

import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AssignmentDataConnector {

    public static int getMaxId(){
        return SQLiteDBConnection.selectHighestId("Assignments", "assignmentId");
    }

    public static List<Assignment> getAssignmentsByClass(int classId){
        return SQLiteDBConnection.selectList(
                "SELECT * FROM Assignments LEFT JOIN AssignmentClasses " +
                        "ON Assignments.studentId = AssignmentClasses.StudentId " +
                        "WHERE AssignmentClasses.classId = " + classId,
                new AssignmentObjectFactory());
    }

    private static class AssignmentObjectFactory implements DBObjectFactory<Assignment>{
        @Override
        public Assignment fromDb(ResultSet rs) throws SQLException {
            return new Assignment(rs);
        }
    }
}
