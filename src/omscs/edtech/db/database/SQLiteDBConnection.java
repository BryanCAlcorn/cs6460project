package omscs.edtech.db.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.sqlite.SQLite;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDBConnection<T> {

    ConnectionSource source;
    Class<T> keyType;

    public SQLiteDBConnection(Class<T> keyType){
        this.keyType = keyType;
        try{
            createConnectionSource();
            TableUtils.createTableIfNotExists(source, keyType);
            destroyConnection();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public Dao<T, Integer> getDao(){
        try {
            createConnectionSource();
            return DaoManager.createDao(source, keyType);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean basicSave(T itemToSave){
        boolean saveSuccessful = true;
        try {
            Dao<T, Integer> dao = this.getDao();
            Dao.CreateOrUpdateStatus status = dao.createOrUpdate(itemToSave);
            this.destroyConnection();
            saveSuccessful = status.isCreated() || status.isUpdated();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            saveSuccessful = false;
        }
        return saveSuccessful;
    }

    public void destroyConnection(){
        try {
            source.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void createConnectionSource() throws SQLException{
        String pathToSQLite = new File("src/omscs/edtech/db/database/TPDatabase.sqlite3").getAbsolutePath();
        source = new JdbcConnectionSource("jdbc:sqlite:" + pathToSQLite);
    }
}
