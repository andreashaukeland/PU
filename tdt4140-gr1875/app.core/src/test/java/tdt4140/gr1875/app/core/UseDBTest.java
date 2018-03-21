package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class UseDBTest {
	
	/*
	 * The methods getFreeID() and connectDB() are being used (and tested) by other methods. 
	 */
	
	int id = UseDB.getFreeID("training"); //used as id if getIDByName is not working

	@Test
	public void DBTest() {
		
		UseDB.addRow("training", id, "TestTrack", "12:00", "2019-03-12", 0, "");
		
		ArrayList<ArrayList<String>> result1 = UseDB.getTable("SELECT * FROM training WHERE trainingid="+id);
		String track = result1.get(result1.size()-1).get(1);
		String geojson = result1.get(result1.size()-1).get(5);
			
		Assert.assertEquals("TestTrack", track);
		Assert.assertEquals("", geojson);
		
		ArrayList<ArrayList<String>> addedTracks = UseDB.getIDByName("training", "place=TestTrack");
		
		for (int i = 0; i < addedTracks.size(); i++) {
			UseDB.deleteRow("training", Integer.parseInt(addedTracks.get(i).get(0)));	
		}		
		Assert.assertTrue(UseDB.getIDByName("training", "place=TestTrack").size() == 0);	
	}


	@Test
	public void getRunnerByIDTest() {
		int id = 1;
		String runner = UseDB.getTable("SELECT firstname, lastname FROM runner WHERE runner.runnerid = " + id).get(0).get(0);
		String result2 = UseDB.getRunnerByID(id).get(0);
		Assert.assertEquals(runner, result2);
	}
	
	@Test
	public void submitWeeklyRunTest() {
		UseDB.submitWeeklyRun("TestTrack2", "2019-03-12", "12:00", 0, "");
		ArrayList<ArrayList<String>> result2 = UseDB.getTable("SELECT trainingid, place FROM training WHERE place='TestTrack2'");
		int id2 = Integer.parseInt(result2.get(0).get(0));
		
		Assert.assertEquals("TestTrack2", result2.get(0).get(1));
		UseDB.deleteRow("training", id2);
	}
	
	@Test
	public void submitTimeToTrainingTest() {
		UseDB.submitTimeToTraining(id, "10:10:10", "test comment");
		ArrayList<ArrayList<String>> result3 = UseDB.getTable("SELECT comment FROM result WHERE comment='test comment'");
		Assert.assertEquals("test comment", result3.get(result3.size()-1).get(0));
		UseDB.deleteRow("result", id);

	}
	
}

