package tdt4140.gr1875.app.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ViewResultsController {

	
	@FXML
    private JFXListView<String> listView;
	
	@FXML
    private JFXButton toggleButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    void OnCancel(ActionEvent event) {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnToggleView(ActionEvent event) {

    }
	
	
}
