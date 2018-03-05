package tdt4140.gr1875.app.ui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import tdt4140.gr1875.app.core.CreateWeeklyRun;

import javafx.stage.StageStyle;

public class CreateWeeklyRunController {

	
	@FXML
	private JFXTextField placeTextField;

	@FXML
	private JFXTextField timeTextField;
	@FXML
	private JFXTextField dateTextField;


	@FXML
	private JFXButton submitButton;

	@FXML
	private JFXButton backButton;
	
	public CreateWeeklyRun createWeeklyRun;
	
	public void initialize(){
		createWeeklyRun = new CreateWeeklyRun();	
	}
	
	@FXML
	public void OnSubmit(ActionEvent event) {
		initialize();
		
		String place = placeTextField.getText();
		String time = timeTextField.getText();
		String date = dateTextField.getText();
		boolean successfulSubmit = createWeeklyRun.submit(place, date, time);
		
		if(! successfulSubmit) {
			createAlert("Not successful submit");
			timeTextField.setText("Error: Wrong Format");
			return;
		}
		createAlert("Successful submit");
		OnBackButton(null);
	}
	
	@FXML
	public void OnBackButton(ActionEvent event) {
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
	}
	
	private void createAlert(String string) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
	}

	
	
	
	
	
}
