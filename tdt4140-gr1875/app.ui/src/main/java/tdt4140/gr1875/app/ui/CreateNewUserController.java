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

public class CreateNewUserController implements Initializable{

	@FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton createUserButton;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField mobileField;
    
    @FXML
    private JFXCheckBox checkCoach;
    
    @FXML
    private JFXCheckBox checkAthlete;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private JFXComboBox<String> yearBox;
    
    @FXML
    private JFXComboBox<String> monthBox;
    
    @FXML
    private JFXComboBox<String> dayBox;
    
    
    //DataBaseHandler handler;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//handler = new DataBaseHandler();
    	yearBox.getItems().addAll(addYears());
    	monthBox.getItems().addAll(addMonths());
    	dayBox.getItems().addAll(addDays());
    }
    

    @FXML
    private void onCreateUser(ActionEvent event) {
    	String userName = usernameField.getText();
    	String pWord = passwordField.getText();
    	String email = emailField.getText();
    	String mobile = mobileField.getText();
    	boolean coach = checkCoach.isSelected();
    	boolean athlete = checkAthlete.isSelected();
    	String birthDay = yearBox.getValue() + "-" + monthBox.getValue() + "-" + dayBox.getValue();
    	
    	if (!checkEmail(email)) {
    		createAlert("Not Valid Email");
    		return;
    	}
    	if (!checkMobile(mobile)) {
    		createAlert("Not Valid Mobile Number");
    		return;
    	}
    	
    	//handler.AddNewUser(name, pWord, email, mobile, birthDay, coach, athlete); Husk å ta hensyn til brukere
    	//som allerede er i databasen (håndteres i dataBaseHandler)
    	
    	if (!validBirthDay(birthDay)) {
    		createAlert("Not Valid Birthday");
    		return;
    	}
    	
    	if (coach) {
    		loadWindow("FxApp.fxml", usernameField);
    	}
    	else if(athlete){
    		loadWindow("AthleteMainScreen.fxml", usernameField);
    	}
    	else {
    		createAlert("You must assign as Coach or Athlete");
    		return;
    	}
    }
   
    private boolean validBirthDay(String birthDay) {
		int month = Integer.parseInt(birthDay.substring(5, 7));
		int day = Integer.parseInt(birthDay.substring(8, 10));
		
		if (month == 2 && day > 28) {
			return false;
		}
		
		if ((month % 2 == 0) && month < 8 && day == 31) {
			return false;
		}
		
		if ((month % 2 == 1) && month > 8 && day == 31) {
			return false;
		}
		
		
		return true;
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
	
	//Checks the mobile number format
	private boolean checkMobile(String mobile) {
		int l = mobile.length();
		if (l != 8) {
			return false;
		}
		
		for (int i = 0; i < l; i++) {
			if (!Character.isDigit(mobile.charAt(i))) {
				return false;
			}
		}
		return true;
		
	}

	//Checks the email format
	private boolean checkEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                 
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		if (!pat.matcher(email).matches()) {
			return false;
		}
		return true;
	}

	//Loads new window on loc. Closes the window that contains the 'root' node.
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
