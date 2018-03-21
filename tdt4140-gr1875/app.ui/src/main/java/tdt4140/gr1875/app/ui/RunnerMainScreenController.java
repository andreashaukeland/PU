package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tdt4140.gr1875.app.core.RunnerMainScreen;
import tdt4140.gr1875.app.core.SessionInformation;

public class RunnerMainScreenController implements Initializable{

	private JFXDrawer drawer;
	@FXML private JFXHamburger hamburger;
	@FXML private StackPane stackPane;
	@FXML private BorderPane borderPane;
	@FXML private JFXTextArea nextRun;
	
	@FXML private TextField timeTextfield;
	@FXML private JFXButton submitButton;
	@FXML private TextField commentTextfield;
	
	private RunnerMainScreen model = new RunnerMainScreen();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDrawer();
		nextRun.setText(model.getLastRun());	
	}
	
	
	private void initDrawer() {
		drawer = new JFXDrawer();
		drawer.setDirection(DrawerDirection.RIGHT);
		drawer.setDefaultDrawerSize(100);
		VBox toolbar;
		try {
			toolbar = FXMLLoader.load(getClass().getResource("RunnerToolbar.fxml"));
			drawer.setSidePane(toolbar);
			
			HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event e) -> {
					task.setRate(task.getRate() * -1);
					task.play();
					
					if (drawer.isShown()) {
						borderPane.setRight(null);
						//drawer.setVisible(true);
						drawer.close();
					}
					else {
						borderPane.setRight(drawer);
						//drawer.setVisible(false);
						drawer.open();;
						
					}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	public void onSubmit() {
		String time = timeTextfield.getText();
		String comment = commentTextfield.getText();
		boolean submitted = model.submitTime(SessionInformation.userId, time, comment);
		if(! submitted) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Could not submit to database");
			alert.showAndWait();
		}
		else {
			timeTextfield.clear();
			commentTextfield.clear();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Time submitted");
			alert.showAndWait();
		}
	}

}
