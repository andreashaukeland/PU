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

	@Test
	public void DBTest() {
		UseDB.putToDB("tracks", UseDB.getFreeID("tracks"), "TestTrack", "12:00", "2019-03-12");
		
		ArrayList<ArrayList<String>> result1 = UseDB.getFromDB("SELECT track FROM tracks");
		String track = result1.get(result1.size()-1).get(0);
			
		Assert.assertEquals("TestTrack", track);
		//Assert.assertEquals("true", "true"); // used to test when ntnu database is down
	}


	@Test
	public void getRunnerByIDTest() {
		int id = 1;
		String runner = UseDB.getFromDB("SELECT fornavn, etternavn FROM runner WHERE runner.runnerid = " + id).get(0).get(0);
		String result2 = UseDB.getRunnerByID(id).get(0);
		Assert.assertEquals(runner, result2);
	}
	
	@After
	public void delete() {
		
	}


}
