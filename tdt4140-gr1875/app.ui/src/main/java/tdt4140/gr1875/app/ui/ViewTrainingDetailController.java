package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.core.ViewTrainingDetail;
import tdt4140.gr1875.app.ui.TrainerMainScreenController.Results;

public class ViewTrainingDetailController implements Initializable{
	private JFXDrawer drawer;
	@FXML private JFXHamburger hamburger;
	@FXML private StackPane stackPane;
	@FXML private BorderPane borderPane;
	String place;
	String time;
	String date;
	String distance;
	String timeUsed;
	String comment;
	
	ViewTrainingDetail viewTrainingDetail = new ViewTrainingDetail();

	//ADD A BUTTON BACK TO PROGRESS PAGE
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDrawer();
		String[] trainingInformation = viewTrainingDetail.getTrainingInformation();
		this.place = trainingInformation[0];
		this.time = trainingInformation[1];
		this.date = trainingInformation[2];
		this.distance = trainingInformation[3];
		this.timeUsed = trainingInformation[4];
		this.comment = trainingInformation[5];

		//SET ALL TEXTFIELDS
					
	}
	
	private void initDrawer() {
		drawer = new JFXDrawer();
		drawer.setDirection(DrawerDirection.RIGHT);
		drawer.setDefaultDrawerSize(100);
		VBox toolbar;
		try {
			if(SessionInformation.userType.equals("runner")) {
				toolbar = FXMLLoader.load(getClass().getResource("RunnerToolbar.fxml"));
			}
			else {
				toolbar = FXMLLoader.load(getClass().getResource("TrainerToolbar.fxml"));
			}
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

	private void initInformationTab() {
		
	}
	private void initMapTab() {
		
	}
	private void initChartTab() {
		
	}

}
	
	

