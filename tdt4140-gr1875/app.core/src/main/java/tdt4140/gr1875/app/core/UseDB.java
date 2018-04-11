package tdt4140.gr1875.app.core;

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
		
	try { conn.close(); stmt.close(); } catch (SQLException e) {/* ignore */}
	
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
	
	public static String getGeojsonTrack(int trackNum) {
		return getTable("SELECT track FROM training WHERE training.trainingid = " + trackNum).get(0).get(0);
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
	
	
	
	// Delete a row in the database, arguments are table name (string) and id (integer)
	public static boolean deleteRow(String table, Integer id) {
		Connection conn = connectDB();
		Statement stmt = null;
		boolean result_status = false;
		
		String table2 = table;
		
		if (table.equals("result")) { // Quick fix for when table name does not equal id name
			table2 = "runner";
		}
		
		try {
			System.out.println("Deleting row with id: " + id + "..");
			stmt = conn.createStatement();
			
			stmt.executeUpdate("DELETE FROM " + table + " WHERE " + table2 + "id like " + id);
			System.out.println("Row deleted!");
			try { conn.close(); } catch (SQLException e) {/* ignore */}
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
	
	public static void addComment(int trainingid, String comment) {
		Connection conn = connectDB();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String query = "Update result"
					+ " set comment = \""+comment+"\"\n"
					+ "where trainingid= "+trainingid+" and runnerid= "+SessionInformation.userId+";";
			System.out.println(query);
		    stmt.execute(query);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try { conn.close(); stmt.close(); } catch (SQLException e) {/* ignore */}
	    System.out.println("Process finished, connection closed");
	}
	
	
	
	// Help function used to get an unused ID
	public static int getFreeID(String table) {		
		int rowNum = 1;
		System.out.println("Finding unused ID...");
		ArrayList<ArrayList<String>> res = getTable("SELECT " + table + "id FROM " + table);
		System.out.println("SELECT " + table + "id FROM " + table);
		ArrayList<String> rs_collapsed = new ArrayList<>();
		res.forEach(elem -> System.out.println(elem));
		res.forEach(elem -> rs_collapsed.addAll(elem));
		while (rs_collapsed.contains(Integer.toString(rowNum))) {
			rowNum++;
		}
	    return rowNum;
	}
	
	//Help function for connecting to an external database.
	private static Connection connectDB() {
		//System.out.println("Connecting to database..." + "\n");
		
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/martisku_db?&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","martisku_pu","pu75");

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	
	
	// Some prebuilt functions
	
	public static boolean submitWeeklyRun(String place, String date, String time, int distance, String track, String officalTraining) {
		int newID = UseDB.getFreeID("training");
		return addRow("training", newID, place, time, date, distance, track, officalTraining);	
	}
	
	public static boolean submitTimeToTraining(int runnerID, String time, String comment) {
		String currentTrainingId = getLastRun().get(0);
		System.out.println("result" + "," + currentTrainingId + "," + runnerID + "," + time);
		if (checkIfResultExists(Integer.parseInt(currentTrainingId), runnerID)) {
			
			return updateTrainingRow(Integer.parseInt(currentTrainingId), runnerID, time, comment);
		}
		else {
			return addRow("result", currentTrainingId,runnerID,time,comment, "(GEOJSON TEXT)");
		}
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
	public static boolean checkIfResultExists(int trainingid, int runnerid) { 
		ArrayList<ArrayList<String>> result = getTable("select * from result where trainingid ="+trainingid+" and runnerid= "+runnerid);
		return result.size()!=0;
	}
	public static boolean updateTrainingRow(int trainingid, int runnerid, String newTime, String comment) { 
		Connection conn = connectDB();
		Statement stmt = null;
		boolean result_status = false;
		
		try {
			System.out.println("Update row with trainingid: " + trainingid + " and runnerid:"+runnerid+"...");
			stmt = conn.createStatement();
			String query = "UPDATE result set time='"+newTime+"', comment='"+comment+"' where trainingid= "+trainingid+" and runnerid= "+runnerid;
			System.out.println(query);
			stmt.executeUpdate(query);
			System.out.println("Row updated!");
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
	
	public static boolean updateCommentToTraining(int trainingID, int runnerID, String time, String comment) {
		System.out.println("result" + "," + trainingID + "," + runnerID + "," + time);
		if (checkIfResultExists(trainingID, runnerID)) {
			return updateTrainingRow(trainingID, runnerID, time,comment);
		}
		else {
			return addRow("result", trainingID,runnerID,time,comment);
		}
	}
	
	public static boolean deleteUserByUsername(String username) {
		boolean deletedProperly = true;
		ArrayList<ArrayList<String>> result = getTable("select loginid, usertype from login where username =\""+username+"\"");
		if(result.size() == 1) {
			int id = Integer.parseInt(result.get(0).get(0));
			String usertype = result.get(0).get(1);
			deletedProperly = deleteRow("login", id) && deleteRow(usertype, id);
		}
		else {
			deletedProperly = false;
		}
		return deletedProperly;
	}
	
	public static int getTrackIDFromPlace(String place) {
		return Integer.parseInt(getTable("select trainingid from training where place = " + "'" + place + "'").get(0).get(0));
	}
	
}
