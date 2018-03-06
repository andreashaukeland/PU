package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    
    private CreateNewUser createNewUser = new CreateNewUser();

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//handler = new DataBaseHandler();
    	yearBox.getItems().addAll(addYears());
    	monthBox.getItems().addAll(addMonths());
    	dayBox.getItems().addAll(addDays());
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
    	
    	if(! checkValidInput(email, mobile, birthday, coach, athlete)) {
    		return;
    	}
    	if(createNewUser.addNewUser(username, password, firstname, lastname, email, mobile, birthday, coach, athlete)) {
        	SceneLoader.loadWindow("LoginScreen.fxml", (Node) firstnameField, this);
    	}
    	
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
