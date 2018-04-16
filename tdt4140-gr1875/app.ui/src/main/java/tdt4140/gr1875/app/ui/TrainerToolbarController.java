package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tdt4140.gr1875.app.core.SessionInformation;

/*
 * This class is used to load the corresponding windows when the buttons in the trainers toolbar are clicked. 
 * 
 */

public class TrainerToolbarController {
		
	
	@FXML private JFXButton CreateTraining;
	@FXML private JFXButton ViewResults;
	@FXML private JFXButton ViewAthletes;
	@FXML private JFXButton SendText;
	@FXML private JFXButton LogOut;
	

	@FXML
	public void loadCreateTraining(ActionEvent event) {
		SceneLoader.loadWindow("CreateWeeklyRun.fxml", (Node) CreateTraining, this);
	}
	
	@FXML
	public void loadViewResults(ActionEvent event) {
		SceneLoader.loadWindow("ViewTrainings.fxml", (Node) CreateTraining, this);
	}
	
	@FXML
	public void loadViewAthletes(ActionEvent event) {
		SceneLoader.loadWindow("ViewRunners.fxml", (Node) CreateTraining, this);
	}
	
	@FXML
	public void loadSettings(ActionEvent event) {
		SceneLoader.loadWindow("Settings.fxml", (Node) CreateTraining, this);
	}
	
	@FXML
	public void onLogOut(ActionEvent event) {
		SessionInformation.userId = 0;
		SessionInformation.userType = "";
		SceneLoader.loadWindow("LoginScreen.fxml", (Node) CreateTraining, this);
		
	}
		
}
