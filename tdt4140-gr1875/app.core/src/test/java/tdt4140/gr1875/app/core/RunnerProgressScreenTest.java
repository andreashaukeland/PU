package tdt4140.gr1875.app.core;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

public class RunnerProgressScreenTest {
	CreateWeeklyRun testRun = new CreateWeeklyRun();
	@Test
	public void getLastRunTest() {
		String place = "Testplace";
		String date = "1111.11.11";
		String time = "11:11";
		int distance = 1;
		String geojson = "";
		int freeid = UseDB.getFreeID("training");
		testRun.submit(place, date, time, distance, geojson);
		String correctString = "The next run is " + time + ":00 " + "1111-11-11" + " at " + place + " ";
		Assert.assertEquals(correctString, RunnerProgressScreen.getLastRun());
		UseDB.deleteRow("training", freeid);
	}
	@Test
	public void submitCommentTest() {
		String place = "Testplace";
		String date = "1111.11.11";
		String time = "11:11";
		int distance = 1;
		String geojson = "";
		int freetrainingid = UseDB.getFreeID("training");
		testRun.submit(place, date, time, distance, geojson);
		
		String testComment = "testComment";
		int runnerid = 1;
		RunnerMainScreen testScreen = new RunnerMainScreen();
		testScreen.submitTime(runnerid, "11:11:11", testComment);
		String actualcomment = UseDB.getTable("select comment from result where trainingid="+freetrainingid).get(0).get(0);
		Assert.assertEquals(testComment, actualcomment);
		UseDB.deleteRow("training",freetrainingid);
		
		
		
		
		
	}
}
