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

    public static Connection getConnection() throws Exception {
        Connection c = null;
        //Class.forName("org.sqlite.JDBC");
        String pathToSQLite = new File("src/omscs/edtech/db/database/TPDatabase.sqlite3").getAbsolutePath();
        c = DriverManager.getConnection("jdbc:sqlite:" + pathToSQLite);
        c.setAutoCommit(false);
/*
        if (this.dbms.equals("mysql")) {
            c = DriverManager.getConnection("jdbc:" + this.dbms + "://" +
                this.serverName + ":" + this.portNumber + "/", connectionProps);
        } else if (this.dbms.equals("derby")) {
            conn = DriverManager.getConnection("jdbc:" + this.dbms + ":" +
                    this.dbName + ";create=true", connectionProps);
        }
*/
        return c;
    }

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

    private void createConnectionSource() throws SQLException{
        String pathToSQLite = new File("src/omscs/edtech/db/database/TPDatabase.sqlite3").getAbsolutePath();
        source = new JdbcConnectionSource("jdbc:sqlite:" + pathToSQLite);
    }

    public void destroyConnection(){
        try {
            source.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static int selectHighestId(String table, String idCol){
        try{
            Connection connection = SQLiteDBConnection.getConnection();
            Statement connectionStatement = connection.createStatement();
            ResultSet resultSet = connectionStatement.executeQuery("SELECT MAX("+idCol+") FROM " + table);
            return resultSet.getInt(0);
        }catch (Exception ex){
            return -1;
        }
    }

    public static boolean executeSQL(String query){
        try
        {
            Connection connection = SQLiteDBConnection.getConnection();
            Statement connectionStatement = connection.createStatement();
            return connectionStatement.execute(query);
        }catch (Exception ex){
            return false;
        }
    }

    public static <T> List<T> selectList(String query, DBObjectFactory<T> factory){
        try {
            Connection connection = SQLiteDBConnection.getConnection();
            Statement connectionStatement = connection.createStatement();
            ResultSet resultSet = connectionStatement.executeQuery(query);
            List<T> dbObjectList = new ArrayList<>();
            while (resultSet.next()){
                T dbObject = factory.fromDb(resultSet);
                dbObjectList.add(dbObject);
            }
            return dbObjectList;
        }catch (Exception ex){
            return null;
        }
    }

    public static <T> T selectSingle(String query, DBObjectFactory<T> factory){
        try {
            Connection connection = SQLiteDBConnection.getConnection();
            Statement connectionStatement = connection.createStatement();
            ResultSet resultSet = connectionStatement.executeQuery(query);
            T dbObject = null;
            while (resultSet.next()){
                dbObject = factory.fromDb(resultSet);
            }
            return dbObject;
        }catch (Exception ex){
            return null;
        }
    }

}
