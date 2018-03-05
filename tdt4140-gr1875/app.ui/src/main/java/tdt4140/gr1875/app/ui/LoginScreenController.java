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
import tdt4140.gr1875.app.core.LoginScreen;
import tdt4140.gr1875.app.core.SessionInformation;

public class LoginScreenController {

	@FXML private JFXTextField usernameField;
    @FXML private JFXButton loginButton;
    @FXML private JFXButton createUserButton;
    @FXML private JFXPasswordField passwordField;
    private LoginScreen loginScreen = new LoginScreen();

    @FXML
    void onCreateUser(ActionEvent event) {
    	loadWindow("CreateNewUser.fxml", usernameField);
    }

    @FXML
    private void onLogin(ActionEvent event) {
    	String username = usernameField.getText();
		String password = passwordField.getText();
		
    	if (! loginScreen.checkUsernameAndPassword(username, password)) {
    		createAlert("Incorrect Username or Password");
    		return;
    	}
    	if(SessionInformation.userType=="runner") {
    		loadWindow("AthleteMainScreen.fxml", usernameField);
    	}
    	else {
    		loadWindow("FxApp.fxml", usernameField);
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
    
	
}
