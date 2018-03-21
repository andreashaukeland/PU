package tdt4140.gr1875.app.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.javascript.object.LatLong;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import tdt4140.gr1875.app.core.CreateWeeklyRun;

import javafx.stage.StageStyle;

public class CreateWeeklyRunController {

	
	@FXML private JFXTextField placeTextField;
	@FXML private JFXTextField timeTextField;
	@FXML private JFXTextField dateTextField;
	@FXML private JFXTextField geojsonTextField;
	@FXML private JFXButton submitButton;
	@FXML private JFXButton backButton;
	public CreateWeeklyRun createWeeklyRun;
	
	public void initialize(){
		createWeeklyRun = new CreateWeeklyRun();	
	}
	
	@FXML
	public void OnSubmit(ActionEvent event) {
		initialize();
		
		String place = placeTextField.getText();
		String time = timeTextField.getText();
		String date = dateTextField.getText();
		String geojsonFilePath = geojsonTextField.getText();
		int distance = getDistanceFromGeojsonFilePath(geojsonFilePath);
		boolean successfulSubmit = createWeeklyRun.submit(place, date, time, distance, geojsonFilePath);
		
		if(! successfulSubmit) {
			return;
		}
		OnBackButton(null);
	}
	
	private int getDistanceFromGeojsonFilePath(String geojsonFilePath) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject track = (JSONObject) parser.parse(new FileReader(geojsonFilePath));
			JSONArray features = (JSONArray) track.get("features");
			
			ArrayList<LatLong> coordinates = new ArrayList<>();
			Iterator<JSONObject> it = features.iterator();
	        
	        while (it.hasNext()) {
	            JSONObject feat = it.next();
				JSONObject geom = (JSONObject) feat.get("geometry");
				JSONArray cords = (JSONArray) geom.get("coordinates");
				coordinates.add( new LatLong((Double) cords.get(1), (Double) cords.get(0)) );
	        }
	        
	        LatLong[] latlongs = coordinates.toArray(new LatLong[coordinates.size()]);
	        int distance = 0;
	        for (int i = 0; i < (latlongs.length - 1); i++) {
				distance += latlongs[i].distanceFrom(latlongs[i+1]);
			}
	        return (int) (distance + latlongs[0].distanceFrom(latlongs[1]));
	        
		}catch (ParseException | IOException e) {
			System.out.println(e);
		}
		return 0;
		
	}

	@FXML
	public void OnBackButton(ActionEvent event) {
		SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) backButton, this);
	}
	
	private void createAlert(String string) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
	}

	
	
	
	
	
}
