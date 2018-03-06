package tdt4140.gr1875.app.core;

import java.util.ArrayList;
import java.util.List;

public class RunnerProgressScreen {

	public RunnerProgressScreen() {
		
	}
	
	public static String getLastRun() {
		ArrayList<String> lastRun = UseDB.getLastRun();
		if(lastRun != null) {
			String lastRunString  = "The next run is " + lastRun.get(2) + " " + lastRun.get(3) + " at " + lastRun.get(1) + " ";
			return lastRunString;
		}
		return null;
	}
	
}
