package tdt4140.gr1875.app.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreateWeeklyRun {
	
	
	//private DatabaseController DBController;
	
	
	public CreateWeeklyRun() {
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean submit(String place, String time) {
		String first = time.substring(0, 4);
		String second = time.substring(5, 7);
		String third = time.substring(8, 10);
		
		List<String> list = new ArrayList<>(Arrays.asList(first, second, third));
		
		boolean isValid = checkValidInput(place, list);
		
		if(isValid) {
			@SuppressWarnings("deprecation")
			Date date = new Date(Integer.parseInt(first), Integer.parseInt(second), Integer.parseInt(third));
			//DBController.askWeeklyRunQuery(date.get(year), date.get(month), date.get(day));
		}
		
		return isValid;
	}
	
	public boolean checkValidInput(String place, List<String> dateArray) {
		if (dateArray.size() != 3) {
			return false;
		}
		try {
			for (int i = 0; i < dateArray.size(); i++) {
				Integer.parseInt(dateArray.get(i));
			}
			return true;
		}
		catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	
	

}
