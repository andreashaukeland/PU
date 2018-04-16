package tdt4140.gr1875.app.core;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

/*
 * This class controls the login operation. Besides the normal login logic it also has a hash function since
 * the password saved in our database is in a hashed format.
 */

public class LoginScreen {
	public LoginScreen() {
	}
	
	public boolean checkUsernameAndPassword(String username, String password) {
		if(username.equals("") || password.equals("")){
			return false;
		}
		ArrayList<ArrayList<String>> table = UseDB.getTable("SELECT salt FROM login WHERE login.username = '" + username + "'");
		String salt ="";
		if (table.size() == 0) {
			System.out.println("Not a valid username/password combination");
			return false;
		}
		else {
			salt = table.get(0).get(0);
		}
    	String saltedPassword = hashPassword(password + salt);
		try{
			ArrayList<String> userInfo = UseDB.getTable("SELECT loginid, usertype FROM login WHERE username=\"" + username + "\" and password=\"" + saltedPassword + "\"").get(0);
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
	
	// This function takes in a password and a salt and returns an encrypted password
    private String hashPassword(String password) {
		MessageDigest messageDigest;
		String encryptedPassword = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(password.getBytes("UTF-8"));
			encryptedPassword = new String(Base64.getEncoder().encode(messageDigest.digest()));
			if (encryptedPassword.contains("'") || encryptedPassword.contains("\"")) {
				encryptedPassword = encryptedPassword.replace("\"", "");
				encryptedPassword = encryptedPassword.replace("'", "");
	        }
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptedPassword;
	}
	
}
