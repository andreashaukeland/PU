package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tdt4140.gr1875.app.core.LoginScreen;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;

/*
 * This class is used to controll the login information and login to a users account.  
 * 
 */

public class LoginScreenController {

	@FXML private JFXTextField usernameField;
    @FXML private JFXButton loginButton;
    @FXML private JFXButton createUserButton;
    @FXML private JFXPasswordField passwordField;
    private LoginScreen loginScreen = new LoginScreen();

    @FXML
    void onCreateUser(ActionEvent event) {
    	SceneLoader.loadWindow("CreateNewUser.fxml", (Node) usernameField, this);
    }

    @FXML
    private void onLogin(ActionEvent event) {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
		boolean validCombination = loginScreen.checkUsernameAndPassword(username, password);
		if (! validCombination) {
    		createAlert("Incorrect Username or Password");
    		return;
    	}
    	if(SessionInformation.userType.equals("runner")) {
    		SceneLoader.loadWindow("RunnerMainScreen.fxml", (Node) usernameField, this);
    	}
    	else {
    		SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) usernameField, this);
    	}
    }
    

    
    private final void createAlert(String string) {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
    }
    
    //login method is fired on ENTER-key
    @FXML
	public void handleKeyPress(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER){
			onLogin(null);
		}
	}
	
}
