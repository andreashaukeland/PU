package tdt4140.gr1875.app.core;

import java.util.ArrayList;
import java.util.List;

/*
 * This class contains the logic behind the progress screen for all runner-users. 
 */

public class RunnerProgressScreen {
	
	public static String getLastRun() {
		ArrayList<String> lastRun = UseDB.getLastRun();
		if(lastRun != null) {
			String lastRunString  = "The next run is " + lastRun.get(2) + " " + lastRun.get(3) + " at " + lastRun.get(1) + " ";
			return lastRunString;
		}
		return null;
	}
	
	// Add a comment to a result
	public static boolean submitComment(int trainingID, int runnerID, String time, String comment) {
		return UseDB.updateCommentToTraining(trainingID, runnerID, time, comment);
	}	
}
