
package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tdt4140.gr1875.app.core.SessionInformation;

/*
 * This class is used to load the corresponding windows when the buttons in the runners toolbar are clicked. 
 * 
 */

public class RunnerToolbarController {

	@FXML private JFXButton nextTraining;
	@FXML private JFXButton ViewResults;
	@FXML private JFXButton ViewAthletes;
	@FXML private JFXButton Chat;
	@FXML private JFXButton Settings;
	

	@FXML
	public void loadNextTraining(ActionEvent event) {
		SceneLoader.loadWindow("RunnerMainScreen.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void loadViewResults(ActionEvent event) {
		SceneLoader.loadWindow("ViewTrainings.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void loadViewProgress(ActionEvent event) {
		SceneLoader.loadWindow("RunnerProgressScreen.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void logOut(ActionEvent event) {
		SessionInformation.userId = 0;
		SessionInformation.userType = "";
		SceneLoader.loadWindow("LoginScreen.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void loadSettings(ActionEvent event) {
		SceneLoader.loadWindow("Settings.fxml", (Node) nextTraining, this);
	}
	
	
}
