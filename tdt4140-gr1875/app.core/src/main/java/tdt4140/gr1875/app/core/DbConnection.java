package tdt4140.gr1875.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DbConnection {
	
	public DbConnection() {
	}
	
	public String db_connect() {
		
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/martisku_db","martisku_pu","pu75");

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		String first = "";
		String last = "";
		
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM runner");

		    if (stmt.execute("SELECT * FROM runner")) {
		        rs = stmt.getResultSet();
		        rs.next();
		        first = rs.getString("fornavn");
		        last = rs.getString("etternavn");
		        System.out.println(first + " " + last);
		    }

		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return first + last;
	}
	
	public static void main(String[] args) {
		System.out.println("Connecting to database..." + "\n");
		System.out.println("Test:" + "\n");
		DbConnection t = new DbConnection();
		String result = t.db_connect();
	}
}
