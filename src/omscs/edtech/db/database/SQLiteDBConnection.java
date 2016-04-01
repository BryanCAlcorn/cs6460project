package omscs.edtech.db.database;

import java.io.File;
import java.sql.*;

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

}
