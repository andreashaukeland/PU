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
		loadWindow("/tdt4140/gr1875/app/ui/ViewResults.fxml", (Node) nextTraining);
	}
	
	@FXML
	public void loadViewAthletes(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/ViewAthletes.fxml", (Node) nextTraining);
	}
	
	@FXML
	public void loadChat(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/Chat.fxml", (Node) nextTraining);
	}
	
	@FXML
	public void loadSettings(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/Settings.fxml", (Node) nextTraining);
	}
	
	
	private void loadWindow(String loc, Node root) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Scene newScene = new Scene(parent);
	    	Stage curStage = (Stage) root.getScene().getWindow();
	    	curStage.setScene(newScene);
	    	
	    	/*
	    	for (ToolbarListener listener : listeners) {
	    		listener.itsACoach(false);
	    	}	
	    	*/
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
