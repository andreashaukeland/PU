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
	
	// Some prebuilt get functions:
	
	public static ArrayList<String> getRunnerByID(int id){
		try {
			return getFromDB("SELECT fornavn, etternavn FROM runner WHERE runner.runnerid = " + id).get(0);

		}catch (Exception e) {
			System.out.println("ID not found");
			return null;
		}
	}
	//TODO MAKE MORE...
	//-----------------------------
	
	
	/*
	 * Method for adding new rows into external database.
	 * 
	 * USAGE: UseDB.putToDB('table', values...) // Values must be of type integer or string
	 * NOTE: To get an unused ID for a new row call the method UseDB.getFreeID('table')
	 * 
	 * EXAMPLE: UseDB.putToDB("tracks", UseDB.getFreeID("tracks"), "Delfino Square", "12:00", "2019-03-12");
	 */
	
	public static void putToDB(String table, Object...objects) {
		Connection conn = connectDB();
		
		try {
			System.out.println("Inserting row into " + table + "...");
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table); // Need rs to get column count
		    java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		    int colNum = rsmd.getColumnCount();
		    String cols = "";
		    
		    for (int i = 1; i < colNum; i++) {
				cols += rsmd.getColumnName(i) + ", ";
			}
		    cols += rsmd.getColumnName(colNum); // MySQL tables are one-indexed
		    
		    String values = "";
		    for (Object val : objects) {
		    	if (val instanceof String) {
		    		values += "'" + val + "', ";
		    	}
		    	else if (val instanceof Integer) {
		    		values += val.toString() + ", ";
		    	}
		    }
		    values = values.substring(0, values.length() - 2);

		    
			String query = "INSERT INTO " + table + " (" + cols + ") VALUES (" + values + ")";
			//System.out.println(query);
			stmt.executeUpdate(query);
			System.out.println("Row added!");
		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
	// Help function used to get an unused ID
	public static int getFreeID(String table) {
		Connection conn = connectDB();
		
		int rowNum = 0;
		System.out.println("Finding unused ID...");
		try {
			Statement stmt = conn.createStatement();
		
	
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
		    java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		    rowNum = rs.last() ? rs.getRow() : 0;
		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	    
	    return rowNum+1;
	}
	
	//Help function for connecting to an external database.
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
