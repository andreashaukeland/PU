package tdt4140.gr1875.app.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Pattern;

public class CreateNewUser {
	LoginScreen checkIfUserExists = new LoginScreen();
	
	public void addNewUser(String username, String password, String firstName, String lastName, 
			String email, String mobile, String birthday, boolean coach, boolean athlete) {
		ArrayList<ArrayList<String>> usernameList = UseDB.getTable("SELECT * FROM login WHERE login.username = '" + username + "'");
		if (usernameList.size() != 0) {
			System.out.println("Username already taken");
			throw new IllegalArgumentException();
		}
		String errorString = checkValidInput(email, mobile, birthday, coach, athlete);
		if(errorString != "") {
			throw new IllegalArgumentException(errorString);
		};
		
		int id = UseDB.getFreeID("login");
		String salt = getSaltString();
		String encryptedPassword = hashPassword(password + salt);
		
		if(!coach) {
			String info = "No info";
			UseDB.addRow("runner", id, firstName, lastName, birthday, email, mobile, info);
			UseDB.addRow("login", id, username, encryptedPassword, salt, "runner");
		}
		else {
			UseDB.addRow("trainer", id, firstName, lastName, birthday, email, mobile);
			UseDB.addRow("login", id, username, encryptedPassword, salt, "trainer");
		}
		
	}
		
	private String checkValidInput(String email, String mobile, String birthday, boolean coach, boolean athlete) {
    	String errorString = "";
    	if (! checkEmail(email)) {
    		errorString = "Not Valid Email \n";
    	}	
    	if (! checkMobile(mobile)) {
    		errorString = "Not Valid Mobile Number \n";
    	}
    	if (! checkBirthDay(birthday)) {
    		errorString = "Not Valid Birthday \n";
    	}
    	if(( coach && athlete ) || (! coach && ! athlete )) {
    		errorString = "Choose either coach or athlete \n";
    	}
    	return errorString;
    }
	
	public boolean checkMobile(String mobile) {
		int l = mobile.length();
		if (l != 8) {
			return false;
		}
		for (int i = 0; i < l; i++) {
			if (!Character.isDigit(mobile.charAt(i))) {
				return false;
			}
		}
		return true;
		
	}
	public boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                 
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		if (!pat.matcher(email).matches()) {
			return false;
		}
		return true;
	}
	
    public boolean checkBirthDay(String birthDay) {
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	    sdf.setLenient(false);
    	    return sdf.parse(birthDay, new ParsePosition(0)) != null;
	}

    public String hashPassword(String password) {
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
    
    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        if (saltStr.contains("'") || saltStr.contains("\"")) {
        	saltStr = saltStr.replace("\"", "");
        	saltStr = saltStr.replace("'", "");
        }
        return saltStr;
    }
    
    //Delete user by username, id and usertype. Example: deleteUser("username", 1, "runner");
    public boolean deleteUser(String username, int id, String usertype) {
    		boolean deleted = false;
    		try {
    			UseDB.deleteRow(usertype, id);
    			UseDB.deleteUserByUsername(username);
    			deleted = true;
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		return deleted;
    }
}
