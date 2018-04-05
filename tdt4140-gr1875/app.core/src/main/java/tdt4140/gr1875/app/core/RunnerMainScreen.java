package tdt4140.gr1875.app.core;

import java.util.ArrayList;
import java.util.List;

public class RunnerMainScreen {
	
	public static String getLastRun() {
		ArrayList<String> lastRun = UseDB.getLastRun();
		if(lastRun != null) {
			String lastRunString  = "The next run is " + lastRun.get(2) + " " + lastRun.get(3) + " at " + lastRun.get(1) + " ";
			return lastRunString;
		}
		return "No next training";
	}
	
	public boolean submitTime(int runnerID, String time, String comment) {
		return UseDB.submitTimeToTraining(runnerID, time, comment);
	}	
	
	public boolean createNewTraining(String place, String time, String date, int distance, String track, String timeUsed, String comment) {
		int runnerId = SessionInformation.userId;
		boolean submittedNewTraining = UseDB.submitWeeklyRun(place, date, time, distance, track, "no");
		boolean submittedTime = UseDB.submitTimeToTraining(runnerId, time, comment);
		return submittedNewTraining && submittedTime;
	}	
}
