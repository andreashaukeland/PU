package tdt4140.gr1875.app.core;

import java.util.ArrayList;

/*
 * This class contains the logic behind getting information for each result.
 */

public class ViewTrainingDetail {

	
	public String[] getTrainingInformation() {
		
		int currentTraining = SessionInformation.currentTrainingViewed;
		int currentRunner = SessionInformation.userType.equals("runner") ?
				SessionInformation.userId : SessionInformation.currentRunnerViewed;
		
		ArrayList<ArrayList<String>> training = UseDB.getTable("Select place, time, date, distance"
				+ " from training where trainingid="+currentTraining);
		String place = training.get(0).get(0).toString();
		String time = training.get(0).get(1).toString();
		String date = training.get(0).get(2).toString();
		String distance = training.get(0).get(3).toString();
		ArrayList<ArrayList<String>> result = UseDB.getTable("Select time, comment from result where trainingid="+currentTraining+" and runnerid="+currentRunner);
		String timeUsed = result.get(0).get(0).toString();
		String comment = result.get(0).get(1).toString();
		String[] resultList = {place, time, date, distance, timeUsed, comment};
		
		return resultList;
	}
}


