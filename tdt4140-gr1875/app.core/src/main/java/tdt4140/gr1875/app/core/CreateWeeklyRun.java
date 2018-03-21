package tdt4140.gr1875.app.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import tdt4140.gr1875.app.core.UseDB;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.control.Alert;
import javafx.scene.media.Track;

import java.io.FileReader;
import java.io.IOException;
import tdt4140.gr1875.app.core.*;

public class CreateWeeklyRun {
	public CreateWeeklyRun() {
	}
	
	public boolean submit(String place, String date, String time, int distance, String geojsonFilePath) {
		if (! checkValidDate(date) || ! checkValidTime(time) || ! checkValidPlace(place)){
			createAlert("Not valid place, date or time format");
			return false;
		}

		if (geojsonFilePath.equals("")) {
			UseDB.submitWeeklyRun(place, date, time, distance, geojsonFilePath);
			return true;
		}

		JSONParser parser = new JSONParser();
		try {
			JSONObject track = (JSONObject) parser.parse(new FileReader(geojsonFilePath));
			UseDB.submitWeeklyRun(place, date, time, distance, track.toString());
			createAlert("Training submitted with track");
			return true;
		} catch (IOException | ParseException e) {
			createAlert("Training submitted without track \nNo geojson file found");
			return true;
		}
		
	}
	
	public boolean checkValidDate(String date) {
		if (date.length() != 10) {
			return false;
		}
		String yearString = date.substring(0, 4);
		String monthString = date.substring(5, 7);
		String dayString = date.substring(8, 10);
		try{
			Integer.parseInt(yearString);
			Integer.parseInt(monthString);
			Integer.parseInt(dayString);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	private boolean checkValidTime(String time) {
		if (time.length() != 5) {
			return false;
		}
		String hourString = time.substring(0, 2);
		String minuteString = time.substring(3, 5);
		
		try{
			Integer.parseInt(hourString);
			Integer.parseInt(minuteString);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	private boolean checkValidPlace(String place) {
		if(place!="") {
			return true;
		}
		return false;
	}
	
	private void createAlert(String string) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
	}
	
}
	