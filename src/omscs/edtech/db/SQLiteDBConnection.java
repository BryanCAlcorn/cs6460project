package omscs.edtech.db;

import java.sql.*;

/**
 * Created by jle on 3/23/2016.
 */
public class SQLiteDBConnection {

    public Connection getConnection() throws Exception {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        //c = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\data\\TPDatabase.sqlite3");
         c = DriverManager.getConnection("jdbc:sqlite:\\GitHub\\cs6460project\\src\\omscs\\edtech\\db\\database\\TPDatabase.sqlite3");
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
