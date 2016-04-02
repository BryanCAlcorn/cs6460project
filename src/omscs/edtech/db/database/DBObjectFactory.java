package omscs.edtech.db.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBObjectFactory<T> {
    abstract T fromDb(ResultSet rs) throws SQLException;
}
