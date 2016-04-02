package omscs.edtech.db.interfaces;

import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Student;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClassDataConnector {

    private static int getMaxId(){
        return SQLiteDBConnection.selectHighestId("Classes", "classId");
    }

    public static List<Class> getActiveClasses(){
        return SQLiteDBConnection.selectList(
                "SELECT * FROM Classes WHERE active LIKE \"true\"",
                new ClassObjectFactory());
    }

    public static Class getEmptyClass(int classId){
        Class singleClass = SQLiteDBConnection.selectSingle(
                "SELECT * FROM Classes WHERE classId = " + classId,
                new ClassObjectFactory());

        return singleClass;
    }

    public static Class getPopulatedClass(int classId){
        Class singleClass = SQLiteDBConnection.selectSingle(
                "SELECT * FROM Classes " +
                "WHERE active LIKE \"true\" AND classId = " + classId,
                new ClassObjectFactory());

        List<Student> students = StudentDataConnector.getStudentsByClass(classId);
        if(students != null) {
            singleClass.setStudents(students);
        }

        List<Assignment> assignments = AssignmentDataConnector.getAssignmentsByClass(classId);
        if(assignments != null){
            singleClass.setAssignments(assignments);
        }

        if(singleClass.getAssignments() != null && singleClass.getStudents() != null) {
            for (Assignment assignment : singleClass.getAssignments()) {
                for (Student student : singleClass.getStudents()) {
                    //Inefficient... lots of DB calls.
                    student.addGrade(assignment.getId(),
                            GradeDataConnector.getGrade(classId, assignment.getId(), student.getId()));
                }
            }
        }

        return singleClass;
    }

    private static class ClassObjectFactory implements DBObjectFactory<Class>{
        @Override
        public Class fromDb(ResultSet rs) throws SQLException {
            return new Class(rs);
        }
    }

}
