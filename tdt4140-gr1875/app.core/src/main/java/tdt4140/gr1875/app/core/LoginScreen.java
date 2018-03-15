package tdt4140.gr1875.app.core;

import java.io.Console;
import java.util.ArrayList;

public class LoginScreen {
	public LoginScreen() {
	}
	
	public boolean checkUsernameAndPassword(String username, String password) {
		try{
			ArrayList<String> userInfo = UseDB.getTable("SELECT loginid, usertype FROM login WHERE username=\"" + username + "\" and password=\"" + password + "\"").get(0);
			int userID = Integer.parseInt(userInfo.get(0));
			String userType = userInfo.get(1);
			SessionInformation.userId = userID;
			SessionInformation.userType = userType;
			return true;
		}
		catch(Exception e){
			System.out.println("Not a valid username/password combination");
			return false;
		}
	}
}
