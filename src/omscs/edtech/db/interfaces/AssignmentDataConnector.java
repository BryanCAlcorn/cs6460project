package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import omscs.edtech.db.model.*;
import omscs.edtech.db.model.Class;

import java.sql.SQLException;
import java.util.*;

public class AssignmentDataConnector {

    private SQLiteDBConnection assignmentConnection;
    private SQLiteDBConnection classAssignmentConnection;
    private GradeDataConnector gradeDataConnector;
    private StudentDataConnector studentDataConnector;
    private OCRFileDataConnector ocrFileDataConnector;

    private Dao<Assignment, Integer> assignmentDao;
    private Dao<ClassAssignment, Integer> classAssignmentDao;

    private PreparedQuery<Assignment> assigmentForClassQuery;

    public AssignmentDataConnector(){
        assignmentConnection = new SQLiteDBConnection(Assignment.class);
        classAssignmentConnection = new SQLiteDBConnection(ClassAssignment.class);
        gradeDataConnector = new GradeDataConnector();
        studentDataConnector = new StudentDataConnector();
        ocrFileDataConnector = new OCRFileDataConnector();
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

    public Assignment getAssignmentById(Integer id){
        try {
            assignmentDao = assignmentConnection.getDao();
            Assignment assignment = assignmentDao.queryForId(id);
            assignmentConnection.destroyConnection();
            return assignment;
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
                //Default all in the current list to Create. If we don't find it in the previously
                //assigned list, then it will stay as Create.
                assignmentMap.put(
                        new ClassAssignment(newlyAssignedClass, assignment), ClassAssignmentStatus.Create);
                for (ClassAssignment previouslyAssignedClass : classAssignments) {
                    if(previouslyAssignedClass.getDbClass().getId() == newlyAssignedClass.getId()){
                        //If the ID's match, we can skip it, since the status hasn't changed
                        assignmentMap.put(previouslyAssignedClass, ClassAssignmentStatus.Skip);
                    }else if(!assignmentMap.containsKey(previouslyAssignedClass) ||
                            assignmentMap.get(previouslyAssignedClass) != ClassAssignmentStatus.Skip){
                        //If we haven't skipped it and it's not in the map yet, then it's possibly
                        //been removed from the list, set it to delete. If we find a match later
                        //it will be skipped.
                        assignmentMap.put(previouslyAssignedClass, ClassAssignmentStatus.Delete);
                    }
                }
            }

            for(ClassAssignment key : assignmentMap.keySet()){
                ClassAssignmentStatus classAssignmentStatus = assignmentMap.get(key);
                if(classAssignmentStatus == ClassAssignmentStatus.Create){
                    classAssignmentDao.create(key);
                    gradeDataConnector.createNewGradesForClass(key.getDbClass(), assignment);
                }else if(classAssignmentStatus == ClassAssignmentStatus.Delete){
                    classAssignmentDao.delete(key);
                    gradeDataConnector.deleteGradesForClass(key.getDbClass());
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

    public boolean deleteAssignmentWithClasses(Assignment assignment){
        boolean deleteSuccessful = true;
        try {
            assignmentDao = assignmentConnection.getDao();
            classAssignmentDao = classAssignmentConnection.getDao();

            deleteSuccessful = assignmentDao.delete(assignment) == 1;

            DeleteBuilder<ClassAssignment, Integer> deleteBuilder = classAssignmentDao.deleteBuilder();
            deleteBuilder.where().eq(ClassAssignment.ASSIGNMENT_COLUMN, assignment.getId());
            deleteSuccessful &= deleteBuilder.delete() >= 1;

            deleteSuccessful &= gradeDataConnector.deleteGradesForAssignment(assignment);
            deleteSuccessful &= ocrFileDataConnector.deleteOCRFileByAssignment(assignment);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            deleteSuccessful = false;
        }
        return deleteSuccessful;
    }

    public List<Assignment> lookupAssignmentsForClass(Class dbClass){
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
