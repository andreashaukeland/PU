package tdt4140.gr1875.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * Help class to enable communication with remote MySQL database
 * 
 * USAGE: From a given class call: UseDB.getTable(query) / UseDB.addRow()
 */



public class UseDB {
	
	// Get table from database
	public static ArrayList<ArrayList<String>> getTable(String query) {
		
		Connection conn = connectDB();
		
		if (conn == null) {
			System.out.println("Can not connect to database");
			System.exit(0);
		}
		
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> table = null;
		
		System.out.println("Running query..");
		
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
		
	try { conn.close(); } catch (SQLException e) {/* ignore */}
	
    System.out.println("Process finished, connection closed");
	return table;
	}
	
	// Some prebuilt get functions:
	
	public static ArrayList<String> getRunnerByID(int id){
		try {
			return getTable("SELECT firstname, lastname FROM runner WHERE runner.runnerid = " + id).get(0);

		}catch (Exception e) {
			System.out.println("ID not found in getRunnerByID");
			return null;
		}
	}
	
	//Arguments example: getIDByName("training", "place=TestTrack"); 
	
	public static ArrayList<ArrayList<String>> getIDByName(String table, String...names) {
		if (names.length > 2 || names.length == 0) {
			System.out.println("Invalid. Wrong number of arguments.");
			return null;
		}
		try {
			String query;
			if (names.length == 1) {
				query = "SELECT " + table + "id FROM " + table + " WHERE " + table + "." 
						+ names[0].split("=")[0] + " = '" + names[0].split("=")[1] + "'";
				System.out.println(query);
			} else {
				query = "SELECT " + table + "id FROM " + table + " WHERE " + table + "." 
						+ names[0].split("=")[0] + " = '" + names[0].split("=")[1] + "' AND " 
						+ names[1].split("=")[0] + " = '" + names[1].split("=")[1] + "'";
			}
			return getTable(query);
		} catch (Exception e) {
			System.out.println("ID not found in getIDByName");
			return null;
		}
	}
	
	//TODO MAKE MORE...
	//-----------------------------
	
	
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
		boolean result_status = false;
		
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
			stmt.executeUpdate(query);
			System.out.println("Row added!");
			result_status = true;
		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		try { conn.close(); } catch (SQLException e) {/* ignore */}
	    System.out.println("Process finished, connection closed");
		return result_status;
	}
	
	
	
	// Delete a row in the database, arguments are table name (string) and id (integer)
	public static boolean deleteRow(String table, Integer id) {
		Connection conn = connectDB();
		boolean result_status = false;
		
		try {
			System.out.println("Deleting row with id: " + id + "..");
			Statement stmt = conn.createStatement();
			
			stmt.executeUpdate("DELETE FROM " + table + " WHERE " + table + "id like " + id);
			System.out.println("Row deleted!");
			try { conn.close(); } catch (SQLException e) {/* ignore */}
			result_status = true;	
		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());		  
		}
		
		try { conn.close(); } catch (SQLException e) {/* ignore */}
	    System.out.println("Process finished, connection closed");
		return result_status;
	}
	public static void addComment(int trainingid, String comment) {
		try {
			Connection myconn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/martisku_db","martisku_pu","pu75");
			Statement statement = myconn.createStatement();
			String query = "Update result"
					+ " set comment = \""+comment+"\"\n"
					+ "where trainingid= "+trainingid+" and runnerid= "+SessionInformation.userId+";";
			System.out.println(query);
		    statement.execute(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	// Help function used to get an unused ID
	public static int getFreeID(String table) {		
		int rowNum = 1;
		System.out.println("Finding unused ID...");
		//ArrayList<ArrayList<String>> res = getTable("SELECT " + table + "id FROM " + table);
		ArrayList<ArrayList<String>> res = getTable("SELECT " + "runnerid FROM " + table);
		ArrayList<String> rs_collapsed = new ArrayList<>();
		res.forEach(elem -> rs_collapsed.addAll(elem));
		while (rs_collapsed.contains(Integer.toString(rowNum))) {
			rowNum++;
		}
	    return rowNum;
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
	
	
	// Some prebuilt functions
	
	public static boolean submitWeeklyRun(String place, String date, String time) {
		int newID = UseDB.getFreeID("training");
		return addRow("training", newID, place, time, date, 0);	
	}
	
	public static boolean submitTimeToTraining(int runnerID, String time, String comment) {
		String currentTrainingId = getLastRun().get(0);
		System.out.println("result" + "," + currentTrainingId + "," + runnerID + "," + time);
		return addRow("result", currentTrainingId, runnerID, time, comment);
	}
	
	public static ArrayList<String> getLastRun() {
		try {
			ArrayList<ArrayList<String>> runs = getTable("SELECT trainingid, place, time, date FROM training");
			ArrayList<String> lastRun = runs.get(runs.size()-1);
			return lastRun;
		}catch (Exception e) {
			System.out.println("ID not found getLastRun");
			return null;
		}
	}

}
