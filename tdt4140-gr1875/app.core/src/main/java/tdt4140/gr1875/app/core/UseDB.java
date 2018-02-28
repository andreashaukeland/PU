package tdt4140.gr1875.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Help class to enable communication with remote MySQL database
 * 
 * USAGE: From a given class call: UseDB.getFromDB(query) / UseDB.putToDB()
 */
public class UseDB {
	
	public static ArrayList<ArrayList<String>> getFromDB(String query) {
		
		Connection conn = connectDB();
		
		if (conn == null) {
			System.out.println("Can not connect to database");
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> table = null;
		
		System.out.println("Running query.." + "\n");
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery(query);
		    java.sql.ResultSetMetaData rsmd = rs.getMetaData();

		    int colNum = rsmd.getColumnCount();
		    table = new ArrayList<ArrayList<String>>();
		    
	        while(rs.next()) {
	        	//System.out.println(rs.getString("fornavn") + " " + rs.getString("etternavn"));
	        	ArrayList<String> col = new ArrayList<String>();
	        	for (int i = 1; i < colNum+1; i++) {
					col.add(rs.getString(i));
				}
	        	table.add(col);
		    }


		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

	return table;
	}
	
	
	public static void putToDB() {
		//TODO
	}
	
	
	private static Connection connectDB() {
		System.out.println("Connecting to database..." + "\n");
		
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/martisku_db","martisku_pu","pu75");

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

}
