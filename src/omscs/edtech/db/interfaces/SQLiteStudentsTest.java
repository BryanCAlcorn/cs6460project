package omscs.edtech.db.interfaces;

import java.sql.*;
import omscs.edtech.db.database.SQLiteDBConnection;

/**
 * Created by jle on 3/22/2016.
 */

public class SQLiteStudentsTest
{
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            c = SQLiteDBConnection.getConnection();

            if (c != null) {
                System.out.println("Connected to the database");
                DatabaseMetaData dm = (DatabaseMetaData) c.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Students;" );
            while ( rs.next() ) {
                int studnetId = rs.getInt("studentId");
                String  firstName = rs.getString("firstName");
                String  lastName = rs.getString("lastName");
                String  eMailAddress = rs.getString("eMailAddress");
                System.out.println( "studnetId = " + studnetId );
                System.out.println( "First Name = " + firstName );
                System.out.println( "Last Name = " + lastName );
                System.out.println( "Email Address = " + eMailAddress );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

    }
}