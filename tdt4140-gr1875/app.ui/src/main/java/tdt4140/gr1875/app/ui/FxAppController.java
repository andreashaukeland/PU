package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FxAppController implements Initializable{

	
	@FXML
	private JFXDrawer drawer;
	
	@FXML
	private JFXHamburger hamburger;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDrawer();
	}
	
	
	private void initDrawer() {
		try {
			VBox toolbar = FXMLLoader.load(getClass().getResource("Toolbar.fxml"));
			drawer.setSidePane(toolbar);
			drawer.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();;
		}
		
		HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
		task.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event e) -> {
				task.setRate(task.getRate() * -1);
				task.play();
				if (drawer.isHidden()) {
					drawer.setVisible(true);
					drawer.open();
				}
				else {
					drawer.setVisible(false);
					drawer.close();
					
				}
		});
		
	}
	
	
	
	/*
	@FXML
	private Button CreateTrainingButton;
	
	@FXML
	private Button ViewTrainingButton;
	
	@FXML
	public void loadViewTraining(ActionEvent event) {
		loadWindow("ViewTraining.fxml");
	}
	
	@FXML
	public void loadCreateTraining(ActionEvent event) {
		loadWindow("CreateWeeklyTraining.fxml");        
	}	
	
	private void loadWindow(String loc) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene(parent));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
}
