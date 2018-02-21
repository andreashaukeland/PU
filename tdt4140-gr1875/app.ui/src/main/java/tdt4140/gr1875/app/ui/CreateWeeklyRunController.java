package tdt4140.gr1875.app.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class CreateWeeklyRunController {

	
	@FXML
	private JFXTextField placeTextField;

	@FXML
	private JFXTextField timeTextField;

	@FXML
	private JFXButton submitButton;

	@FXML
	private JFXButton cancelButton;
	
	public CreateWeeklyRun createWeeklyRun;
	
	public void initialize(){
		createWeeklyRun = new CreateWeeklyRun();	
	}
	
	@FXML
	public void OnSubmit(ActionEvent event) {
		initialize();
		
		String place = placeTextField.getText();
		String time = timeTextField.getText();
		boolean successfulSubmit = createWeeklyRun.submit(place, time);
		
		if(! successfulSubmit) {
			//Put error message in FXML
			placeTextField.setText("Error: Wrong Format"); 
		}
		
		OnCancel(null);
	}
	
	@FXML
	public void OnCancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
	}

	
	
	
	
	
}
