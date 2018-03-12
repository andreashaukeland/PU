package tdt4140.gr1875.app.core;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class CreateNewUser {
	
	public boolean addNewUser(String username, String password, String salt, String firstName, String lastName, 
			String email, String mobile, String birthday, boolean coach, boolean athlete) {
		System.out.println("salt: " + salt);
		if(!coach) {
			int id = UseDB.getFreeID("runner");
			boolean addedUser = UseDB.addRow("runner", id, firstName, lastName, birthday, email, mobile);
			boolean addedLogin = UseDB.addRow("login", id, username, password, salt, "runner");
			return (addedUser && addedLogin);
		}
		else {
			int id = UseDB.getFreeID("trainer");
			boolean addedUser = UseDB.addRow("trainer", id, firstName, lastName, birthday, email, mobile);
			boolean addedLogin = UseDB.addRow("login", id, username, password, salt, "trainer");
			return (addedUser && addedLogin);
		}
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
	
    public boolean validBirthDay(String birthDay) {
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	    sdf.setLenient(false);
    	    return sdf.parse(birthDay, new ParsePosition(0)) != null;
	}

}
