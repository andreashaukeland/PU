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
}
