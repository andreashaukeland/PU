package tdt4140.gr1875.app.ui;

import java.io.IOException;

import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ToolbarController {
		
	
	@FXML 
	private JFXButton CreateTraining;
	
	@FXML 
	private JFXButton ViewResults;
	
	@FXML 
	private JFXButton ViewAthletes;
	
	@FXML 
	private JFXButton SendText;
	
	@FXML 
	private JFXButton Settings;
	
	
	@FXML
	public void loadCreateTraining(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/CreateWeeklyRun.fxml");
	}
	
	@FXML
	public void loadViewResults(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/ViewResults.fxml");
	}
	
	@FXML
	public void loadViewAthletes(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/ViewAthletes.fxml");
	}
	
	@FXML
	public void loadSendText(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/SendText.fxml");
	}
	
	@FXML
	public void loadSettings(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/Settings.fxml");
	}
	
	
	private void loadWindow(String loc) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
