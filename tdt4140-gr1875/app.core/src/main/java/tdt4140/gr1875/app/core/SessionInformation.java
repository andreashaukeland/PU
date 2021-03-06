package tdt4140.gr1875.app.core;

/*
 * This class is used to make a session object for every user that is logged on the application. In this way
 * it is possible to know whether the used that is currently logged on is a trainer or a runner.
 */

public class SessionInformation {
	public static String userType = "trainer";
	public static int userId = 0;
	//When a trainer wants to see the progress of a runner the currentUser needs to be stored
	public static int currentRunnerViewed = 0;
	public static int currentTrainingViewed = 0;
	//When change in trackresults and trackmap view
	public static int currentTrackLoaded = 1;

}
