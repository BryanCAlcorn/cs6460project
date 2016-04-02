package omscs.edtech.db.interfaces;

import omscs.edtech.db.database.DBObjectFactory;
import omscs.edtech.db.database.SQLiteDBConnection;
import omscs.edtech.db.model.Class;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClassDataConnector {

    public static List<Class> getActiveClasses(){
        return SQLiteDBConnection.selectList("SELECT * FROM Classes WHERE active LIKE \"true\"",
                new ClassObjectFactory());
    }

    public static Class getClass(int classId){
        return SQLiteDBConnection.selectSingle("SELECT * FROM Classes WHERE classId=" + classId,
                new ClassObjectFactory());
    }

    private static class ClassObjectFactory implements DBObjectFactory<Class>{
        @Override
        public Class fromDb(ResultSet rs) throws SQLException {
            return new Class(rs);
        }
    }

}
