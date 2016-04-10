package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.ClassAssignment;
import omscs.edtech.db.model.Class;

import java.sql.SQLException;
import java.util.*;

public class AssignmentDataConnector {

    private SQLiteDBConnection assignmentConnection;
    private SQLiteDBConnection classAssignmentConnection;

    private Dao<Assignment, Integer> assignmentDao;
    private Dao<ClassAssignment, Integer> classAssignmentDao;

    private PreparedQuery<Assignment> assigmentForClassQuery;

    public AssignmentDataConnector(){
        assignmentConnection = new SQLiteDBConnection(Assignment.class);
        classAssignmentConnection = new SQLiteDBConnection(ClassAssignment.class);
    }

    public List<Assignment> getAssignments(){
        try {
            assignmentDao = assignmentConnection.getDao();
            List<Assignment> assignments = assignmentDao.queryForAll();
            assignmentConnection.destroyConnection();
            return assignments;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private enum ClassAssignmentStatus{
        Skip,
        Create,
        Delete
    }

    public boolean saveAssignmentWithClasses(Assignment assignment) {
        boolean saveSuccessful = true;
        try {
            assignmentDao = assignmentConnection.getDao();
            classAssignmentDao = classAssignmentConnection.getDao();
            Dao.CreateOrUpdateStatus status = assignmentDao.createOrUpdate(assignment);
            saveSuccessful &= status.isCreated() || status.isUpdated();

            List<ClassAssignment> classAssignments =
                    classAssignmentDao.queryForEq(ClassAssignment.ASSIGNMENT_COLUMN, assignment.getId());
            Map<ClassAssignment, ClassAssignmentStatus> assignmentMap = new HashMap<>();
            for(Class newlyAssignedClass : assignment.getDbClasses()) {
                assignmentMap.put(
                        new ClassAssignment(newlyAssignedClass, assignment), ClassAssignmentStatus.Create);
                for (ClassAssignment previouslyAssignedClass : classAssignments) {
                    if(previouslyAssignedClass.getDbClass().getId() == newlyAssignedClass.getId()){
                        assignmentMap.put(previouslyAssignedClass, ClassAssignmentStatus.Skip);
                    }else if(!assignmentMap.containsKey(previouslyAssignedClass) ||
                            assignmentMap.get(previouslyAssignedClass) != ClassAssignmentStatus.Skip){
                        assignmentMap.put(previouslyAssignedClass, ClassAssignmentStatus.Delete);
                    }
                }
            }

            for(ClassAssignment key : assignmentMap.keySet()){
                ClassAssignmentStatus classAssignmentStatus = assignmentMap.get(key);
                if(classAssignmentStatus == ClassAssignmentStatus.Create){
                    classAssignmentDao.create(key);
                }else if(classAssignmentStatus == ClassAssignmentStatus.Delete){
                    classAssignmentDao.delete(key);
                }
            }

            assignmentConnection.destroyConnection();
            classAssignmentConnection.destroyConnection();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            saveSuccessful = false;
        }
        return saveSuccessful;
    }

    public List<Assignment> lookupAssignmentsForClasses(Class dbClass){
        try {
            classAssignmentDao = classAssignmentConnection.getDao();
            assignmentDao = assignmentConnection.getDao();
            if (assigmentForClassQuery == null) {
                assigmentForClassQuery = makeAssignmentsForClassQuery();
            }

            assigmentForClassQuery.setArgumentHolderValue(0, dbClass);
            List<Assignment> assignments = assignmentDao.query(assigmentForClassQuery);
            assignmentConnection.destroyConnection();
            classAssignmentConnection.destroyConnection();
            return assignments;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private PreparedQuery<Assignment> makeAssignmentsForClassQuery() throws SQLException {
        // build our inner query for UserPost objects
        QueryBuilder<ClassAssignment, Integer> userPostQb = classAssignmentDao.queryBuilder();
        // just select the post-id field
        userPostQb.selectColumns(ClassAssignment.ASSIGNMENT_COLUMN);
        SelectArg userSelectArg = new SelectArg();
        // you could also just pass in user1 here
        userPostQb.where().eq(ClassAssignment.CLASS_COLUMN, userSelectArg);

        // build our outer query for Post objects
        QueryBuilder<Assignment, Integer> postQb = assignmentDao.queryBuilder();
        // where the id matches in the post-id from the inner query
        postQb.where().in(Assignment.ID_COLUMN, userPostQb);
        return postQb.prepare();
    }
}
