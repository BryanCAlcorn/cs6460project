package omscs.edtech.db.interfaces;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.LazyForeignCollection;
import com.j256.ormlite.field.FieldType;
import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Assignment;
import omscs.edtech.db.model.Class;
import omscs.edtech.db.model.Student;
import omscs.edtech.ui.controls.IntegerField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClassDataConnector {

    private SQLiteDBConnection connection;
    private Dao<Class, Integer> classDao;

    public ClassDataConnector(){
        connection = new SQLiteDBConnection(Class.class);
    }

    public Class getClassById(int id){
        try {
            classDao = connection.getDao();
            Class dbClass = classDao.queryForId(id);
            connection.destroyConnection();
            return dbClass;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Class> getActiveClasses(){
        try {
            classDao = connection.getDao();
            List<Class> classes = classDao.queryForEq("active", true);
            connection.destroyConnection();
            return classes;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Class> getAllClasses(){
        try {
            classDao = connection.getDao();
            List<Class> classes = classDao.queryForAll();
            connection.destroyConnection();
            return classes;
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean saveClass(Class dbClass) {
        boolean saveSuccessful = true;
        try {
            classDao = connection.getDao();
            Dao.CreateOrUpdateStatus status = classDao.createOrUpdate(dbClass);
            connection.destroyConnection();
            saveSuccessful = status.isCreated() || status.isUpdated();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            saveSuccessful = false;
        }
        return saveSuccessful;
    }

}
