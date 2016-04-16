package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.ClassAssignment;

import java.sql.SQLException;
import java.util.List;

public class ClassDataConnector {

    private SQLiteDBConnection classConnection;
    private SQLiteDBConnection classAssignmentConnection;

    private Dao<Class, Integer> classDao;
    private Dao<ClassAssignment, Integer> classAssignmentDao;

    private PreparedQuery<Class> classForAssignmentQuery;

    public ClassDataConnector(){
        classConnection = new SQLiteDBConnection(Class.class);
        classAssignmentConnection = new SQLiteDBConnection(ClassAssignment.class);
    }

    public Class getClassById(int id){
        try {
            classDao = classConnection.getDao();
            Class dbClass = classDao.queryForId(id);
            classConnection.destroyConnection();
            return dbClass;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Class> getActiveClasses(){
        try {
            classDao = classConnection.getDao();

            QueryBuilder<Class, Integer> query = classDao.queryBuilder();

            query.where().eq(Class.ACTIVE_COL, true);
            query.orderBy(Class.YEAR_COL, true).orderBy(Class.PERIOD_COL, true);
            List<Class> classes = query.query();

            classConnection.destroyConnection();
            return classes;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Class> getAllClasses(){
        try {
            classDao = classConnection.getDao();
            List<Class> classes = classDao.queryForAll();
            classConnection.destroyConnection();
            return classes;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean saveClass(Class dbClass) {
        return classConnection.basicSave(dbClass);
    }

    public List<Class> lookupClassesForAssignment(Assignment assignment){
        try {
            classAssignmentDao = classAssignmentConnection.getDao();
            classDao = classConnection.getDao();
            if (classForAssignmentQuery == null) {
                classForAssignmentQuery = makeClassesForAssignmentQuery();
            }
            classForAssignmentQuery.setArgumentHolderValue(0, assignment);
            List<Class> assignments = classDao.query(classForAssignmentQuery);
            classConnection.destroyConnection();
            classAssignmentConnection.destroyConnection();
            return assignments;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private PreparedQuery<Class> makeClassesForAssignmentQuery() throws SQLException {
        // build our inner query for UserPost objects
        QueryBuilder<ClassAssignment, Integer> userPostQb = classAssignmentDao.queryBuilder();
        // just select the post-id field
        userPostQb.selectColumns(ClassAssignment.CLASS_COLUMN);
        SelectArg userSelectArg = new SelectArg();
        // you could also just pass in user1 here
        userPostQb.where().eq(ClassAssignment.ASSIGNMENT_COLUMN, userSelectArg);

        // build our outer query for Post objects
        QueryBuilder<Class, Integer> postQb = classDao.queryBuilder();
        // where the id matches in the post-id from the inner query
        postQb.where().in(Class.ID_COLUMN, userPostQb);
        return postQb.prepare();
    }

}
