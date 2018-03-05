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
	private JFXButton LogOut;
	
	
	List<ToolbarListener> listeners = new ArrayList<ToolbarListener>();
	
	
	public void addListener(ToolbarListener listener) {
		listeners.add(listener);
	}
	

	@FXML
	public void loadCreateTraining(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/CreateWeeklyRun.fxml", (Node) CreateTraining);
	}
	
	@FXML
	public void loadViewResults(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/ViewResults.fxml", (Node) CreateTraining);
	}
	
	@FXML
	public void loadViewAthletes(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/ViewAthletes.fxml", (Node) CreateTraining);
	}
	
	@FXML
	public void loadSendText(ActionEvent event) {
		loadWindow("/tdt4140/gr1875/app/ui/SendText.fxml", (Node) CreateTraining);
	}
	
	@FXML
	public void onLogOut(ActionEvent event) {
		SessionInformation.userId = 0;
		SessionInformation.userType = "";
		loadWindow("/tdt4140/gr1875/app/ui/LoginScreen.fxml", (Node) CreateTraining);
		
	}
	
	
	private void loadWindow(String loc, Node root) {
		System.out.println("Listeners: " + listeners);
		for (ToolbarListener listener : listeners) {
    		listener.itsACoach(true);
    	}
		try {
    		Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Scene newScene = new Scene(parent);
	    	Stage curStage = (Stage) root.getScene().getWindow();
	    	
	    	curStage.setScene(newScene);
	   
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
