package tdt4140.gr1875.app.core;

import java.util.ArrayList;
import java.util.List;

public class AthleteMainScreen {

	public AthleteMainScreen() {
		
	}
	
	public static String getLastRun() {
		ArrayList<String> lastRun = UseDB.getLastRun();
		if(lastRun != null) {
			String lastRunString  = "The next run is " + lastRun.get(1) + " " + lastRun.get(2) + " at " + lastRun.get(0) + " ";
			return lastRunString;
		}
		return null;
	}
	
}
