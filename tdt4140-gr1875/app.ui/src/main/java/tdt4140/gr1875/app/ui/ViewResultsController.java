package tdt4140.gr1875.app.ui;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewResultsController {

	
	@FXML
    private JFXListView<String> listView;
	
	@FXML
    private JFXButton toggleButton;

    @FXML
    private JFXButton backButton;

    @FXML
    void OnBackButton(ActionEvent event) {
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

    @FXML
    void OnToggleView(ActionEvent event) {

    }
	
	
}
