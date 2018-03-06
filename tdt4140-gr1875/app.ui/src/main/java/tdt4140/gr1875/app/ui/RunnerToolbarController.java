
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

public class RunnerToolbarController {

	@FXML 
	private JFXButton nextTraining;
	
	@FXML 
	private JFXButton ViewResults;
	
	@FXML 
	private JFXButton ViewAthletes;
	
	@FXML 
	private JFXButton Chat;
	
	@FXML 
	private JFXButton Settings;
	
	/*
	List<ToolbarListener> listeners = new ArrayList<ToolbarListener>();
	
	
	public void addListener(ToolbarListener listener) {
		listeners.add(listener);
	}
	*/
	
	@FXML
	public void loadNextTraining(ActionEvent event) {
		//loadWindow("/tdt4140/gr1875/app/ui/ViewNextTrainings.fxml", (Node) CreateTraining);
	}
	
	@FXML
	public void loadViewResults(ActionEvent event) {
		SceneLoader.loadWindow("ViewResults.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void loadViewAthletes(ActionEvent event) {
		SceneLoader.loadWindow("RunnerProgressScreen.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void loadChat(ActionEvent event) {
		SceneLoader.loadWindow("Chat.fxml", (Node) nextTraining, this);
	}
	
	@FXML
	public void loadSettings(ActionEvent event) {
		SceneLoader.loadWindow("Settings.fxml", (Node) nextTraining, this);
	}
	
	
}
