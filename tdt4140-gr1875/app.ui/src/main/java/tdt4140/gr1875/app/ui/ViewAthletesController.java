package tdt4140.gr1875.app.ui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewAthletesController { //implements ToolbarListener

	@FXML
    private JFXListView<String> listView;

    @FXML
    private JFXButton toggleViewButton;

    @FXML
    private JFXButton backButton;
    
    //private boolean itIsACoach;

    @FXML
    void OnBackButton(ActionEvent event) {
    	Stage curstage = (Stage) backButton.getScene().getWindow();
    	curstage.close();
    	
    	
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	/*
    	System.out.println("Its a coach: " + itIsACoach);
    	
    	if (itIsACoach) {
    		try {
    			Parent parent = FXMLLoader.load(getClass().getResource("FxApp.fxml"));
    			Stage stage = new Stage(StageStyle.DECORATED);
    			stage.setScene(new Scene(parent));
    			stage.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}else {
    		try {
    			Parent parent = FXMLLoader.load(getClass().getResource("AthleteMainScreen.fxml"));
    			Stage stage = new Stage(StageStyle.DECORATED);
    			stage.setScene(new Scene(parent));
    			stage.show();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	@Override
		public void itsACoach(boolean coach) {
			System.out.println("HEY");
			this.itIsACoach = coach;
		}
    	
    	*/
    }

    @FXML
    void OnToggleButton(ActionEvent event) {

    }


}
