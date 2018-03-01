package tdt4140.gr1875.app.ui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginScreenController {

	@FXML
    private JFXTextField usernameField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton createUserButton;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXCheckBox checkCoach;

    @FXML
    private JFXCheckBox checkAthlete;


    @FXML
    void onCreateUser(ActionEvent event) {
    	loadWindow("CreateNewUser.fxml", usernameField);
    }

    @FXML
    private void onLogin(ActionEvent event) {
    	if (!checkUsernameAndPassword()) {
    		createAlert("Incorect Username or Password");
    		return;
    	}
    	if (checkCoach.isSelected()) {
    		loadWindow("FxApp.fxml", usernameField);
    	}
    	else if (checkAthlete.isSelected()) {
    		loadWindow("AthleteMainScreen.fxml", usernameField);
    	}
    	else if (!checkAthlete.isSelected() && !checkCoach.isSelected()){
    		createAlert("You must check for either 'Coach' og 'Athlete'");
    		return;
    	}
    	
    }
    
    private void loadWindow(String loc, Node root) {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Scene newScene = new Scene(parent);
	    	Stage curStage = (Stage) root.getScene().getWindow();
	    	curStage.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private final void createAlert(String string) {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
    }
    
	private boolean checkUsernameAndPassword() {
		if (usernameField.getText().isEmpty()) { return false;}
		if (passwordField.getText().isEmpty()) { return false;}
		return true;
	}

	@FXML
	private void OnCheckedCoach() {
		if (checkAthlete.isSelected()) {
			checkAthlete.setSelected(false);
		}
	}

	@FXML
	private void OnCheckedAthlete() {
		if (checkCoach.isSelected()) {
			checkCoach.setSelected(false);
		}
	}
	
}
