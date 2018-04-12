package tdt4140.gr1875.web.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.result.UpdatableResultSet;

/*
 * Help class to enable communication with remote MySQL database
 * 
 * USAGE: From a given class call: UseDB.getTable(query) / UseDB.addRow()
 */

public class DatabaseConnector {

	public static ArrayList<ArrayList<String>> getRow(String table, List<String> names) {
		
		try {
			String query = "SELECT * FROM " + table;
			if (!names.isEmpty()) {
				query += " WHERE " + names.get(0);
			}
			
			for (int i = 1; i < names.size(); i++) {
				query += " AND " + names.get(i);
			}
			System.out.println(query);
			return getTable(query);
		} catch (Exception e) {
			System.out.println("ERROR:" + e);
			return null;
		}
	}	

	/*
	 * Method for adding new rows into external database.
	 * 
	 * USAGE: UseDB.addRow('table', values...) // Values must be of type integer or string
	 * NOTE: To get an unused ID for a new row call the method UseDB.getFreeID('table')
	 * 
	 * EXAMPLE: UseDB.addRow("tracks", UseDB.getFreeID("tracks"), "Delfino Square", "12:00", "2019-03-12");
	 */
	
	public static boolean addRow(String table, Object...objects) {
		Connection conn = connectDB();
		Statement stmt = null;
		boolean result_status = false;
		
		try {
			System.out.println("Inserting row into " + table + "...");
			stmt = conn.createStatement();
			
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
			stmt.executeUpdate(query);
			System.out.println("Row added!");
			result_status = true;
		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		try { conn.close(); stmt.close(); } catch (SQLException e) {/* ignore */}
	    System.out.println("Process finished, connection closed");
		return result_status;
	}

	//Help function for connecting to an external database.
	private static Connection connectDB() {
		//System.out.println("Connecting to database..." + "\n");
		
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/martisku_db?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","martisku_pu","pu75");

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	
	}
	
}

