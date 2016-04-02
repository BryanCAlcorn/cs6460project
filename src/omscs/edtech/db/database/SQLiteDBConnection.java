package omscs.edtech.db.database;

import org.sqlite.SQLite;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jle on 3/23/2016.
 */
public class SQLiteDBConnection {

    public static Connection getConnection() throws Exception {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
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
