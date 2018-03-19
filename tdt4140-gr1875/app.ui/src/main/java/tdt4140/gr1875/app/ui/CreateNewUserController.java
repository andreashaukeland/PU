package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tdt4140.gr1875.app.core.CreateNewUser;
import tdt4140.gr1875.app.core.SessionInformation;

public class CreateNewUserController implements Initializable{
	@FXML private JFXTextField firstnameField;
	@FXML private JFXTextField lastnameField;
	@FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXButton createUserButton;
    @FXML private JFXTextField emailField;
    @FXML private JFXTextField mobileField;
    @FXML private JFXCheckBox checkCoach;
    @FXML private JFXCheckBox checkAthlete;
    @FXML private AnchorPane anchorPane;
    @FXML private JFXComboBox<String> yearBox;
    @FXML private JFXComboBox<String> monthBox;
    @FXML private JFXComboBox<String> dayBox;
    @FXML private JFXButton backButton;
    
    private CreateNewUser createNewUser;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//handler = new DataBaseHandler();
    	yearBox.getItems().addAll(addYears());
    	monthBox.getItems().addAll(addMonths());
    	dayBox.getItems().addAll(addDays());
    	createNewUser = new CreateNewUser();
    }
    

    private String hashPassword(String password) {
		MessageDigest messageDigest;
		String encryptedPassword = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(password.getBytes("UTF-8"));
			encryptedPassword = new String(Base64.getEncoder().encode(messageDigest.digest()));
			if (encryptedPassword.contains("'") || encryptedPassword.contains("\"")) {
				encryptedPassword = encryptedPassword.replace("\"", "");
				encryptedPassword = encryptedPassword.replace("'", "");
	        }
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptedPassword;
	}
    
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        if (saltStr.contains("'") || saltStr.contains("\"")) {
        	saltStr = saltStr.replace("\"", "");
        	saltStr = saltStr.replace("'", "");
        }
        return saltStr;
    }
    
    @FXML
    private void onCreateUser(ActionEvent event) {
    	String firstname = firstnameField.getText();
    	String lastname = lastnameField.getText();
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	String email = emailField.getText();
    	String mobile = mobileField.getText();
    	boolean coach = checkCoach.isSelected();
    	boolean athlete = checkAthlete.isSelected();
    	String birthday = yearBox.getValue() + "-" + monthBox.getValue() + "-" + dayBox.getValue();
    	String salt = getSaltString();
    	String encryptedPassword = hashPassword(password + salt);
    	
    	if(! checkValidInput(email, mobile, birthday, coach, athlete)) {
    		return;
    	}
    	if(createNewUser.addNewUser(username, encryptedPassword, salt, firstname, lastname, email, mobile, birthday, coach, athlete)) {
        	SceneLoader.loadWindow("LoginScreen.fxml", (Node) firstnameField, this);
    	}
    	
    }
    
    @FXML
    void OnBackButton() {
    	SceneLoader.loadWindow("LoginScreen.fxml", (Node) firstnameField, this);
    }
   
    private boolean checkValidInput(String email, String mobile, String birthday, boolean coach, boolean athlete) {
    	boolean validInput = true;
    	if (! createNewUser.checkEmail(email)) {
    		createAlert("Not Valid Email");
    		validInput = false;
    	}
    	if (!createNewUser.checkMobile(mobile)) {
    		createAlert("Not Valid Mobile Number");
    		validInput = false;
    	}
    	if (!createNewUser.validBirthDay(birthday)) {
    		createAlert("Not Valid Birthday");
    		validInput = false;
    	}
    	if(( coach && athlete ) || (! coach && ! athlete )) {
    		createAlert("Please check the box for either coach or athlete");
    		validInput = false;
    	}
    	return validInput;
    }

	//Makes sure that you can't check for both Coach and Athlete
    @FXML
    private void onCheckCoach() {
    	if (checkAthlete.isSelected()) {
    		checkAthlete.setSelected(false);
    	}
    }

    @FXML
    private void onCheckAthlete() {
    	if (checkCoach.isSelected()) {
    		checkCoach.setSelected(false);
    	}
    }
    
    //Make an alert with customized text
	private void createAlert(String string) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
	}



    
    private List<String> addYears(){
    	List<String> newList = new ArrayList<>();
    	for (int i = 1950; i < 2018; i++) {
			newList.add(Integer.toString(i));
		}
    	return newList;
    }
	
    private List<String> addMonths(){
    	List<String> newList = new ArrayList<>();
    	for (int i = 1; i < 13; i++) {
    		if (i < 10) {
    			newList.add('0' + Integer.toString(i));
    		}else {
    			newList.add(Integer.toString(i));
    		}
    	}
    	return newList;
    }
    
    private List<String> addDays(){
    	List<String> newList = new ArrayList<>();
    	for (int i = 1; i < 32; i++) {
    		if (i < 10) {
    			newList.add('0' + Integer.toString(i));
    		}else {
    			newList.add(Integer.toString(i));
    		}
		}
    	return newList;
    }
}
