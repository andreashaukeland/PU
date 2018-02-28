package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
    
    //DataBaseHandler handler;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	//handler = new DataBaseHandler();
	}

    @FXML
    private void onCreateUser(ActionEvent event) {
    	String userName = usernameField.getText();
    	String pWord = passwordField.getText();
    	String email = emailField.getText();
    	String mobile = mobileField.getText();
    	boolean coach = checkCoach.isSelected();
    	boolean athlete = checkAthlete.isSelected();
    	
    	if (!checkEmail(email)) {
    		createAlert("Not Valid Email");
    		return;
    	}
    	if (!checkMobile(mobile)) {
    		createAlert("Not Valid Mobile Number");
    		return;
    	}
    	
    	//handler.AddNewUser(userName, pWord, email, mobile, coach, athlete); Husk å ta hensyn til brukernavn
    	//som allerede er i databasen (håndteres i dataBaseHandler)
    	
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
    
	private void createAlert(String string) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
	}

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

	
	
}
