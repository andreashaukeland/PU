package tdt4140.gr1875.app.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ViewAthletesController {

	 @FXML
	    private JFXListView<String> listView;

	    @FXML
	    private JFXButton toggleViewButton;

	    @FXML
	    private JFXButton cancelButton;

	    @FXML
	    void OnCancel(ActionEvent event) {
	    	Stage stage = (Stage) cancelButton.getScene().getWindow();
	        stage.close();
	    }

	    @FXML
	    void OnToggleButton(ActionEvent event) {

	    }

}
