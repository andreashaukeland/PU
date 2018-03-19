package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.ui.RunnerProgressScreenController.Results;


public class TrainerMainScreenController implements Initializable{

	//private JFXDrawer drawer;
	@FXML private JFXHamburger hamburger;
	@FXML private StackPane stackPane;
	@FXML private BorderPane borderPane;
	@FXML private GridPane trainingMapTabGridPane;
	private JFXDrawer drawer = new JFXDrawer();
	
	@FXML private TableView<Training> tableView;
	@FXML private TableColumn<Training, String> trainingPlaceColumn;
	@FXML private TableColumn<Training, String> trainingTimeColumn;
	@FXML private TableColumn<Training, String> trainingDateColumn;
	GoogleMapController GMC = new GoogleMapController();
	
	@FXML
	public void onLoadTrack() { //TODO: make GoogleMapLogic class in core. Move GMapsFX dependency to both core and ui. Load the selected track
		Training obj = tableView.getSelectionModel().getSelectedItem();
		if (obj != null) {
			System.out.println(obj.getTrainingPlace());
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDrawer();
		initMap();
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT place, time, date from training;");
		tableView.getItems().setAll(getResults(list));
	}
	
	
	private void initMap() {
		try {
			GridPane pane = FXMLLoader.load(getClass().getResource("GoogleMap.fxml"));
			trainingMapTabGridPane.getChildren().setAll(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initCol() {
		trainingPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingPlace"));
		trainingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
		trainingDateColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingDate"));
	}
	
	private ObservableList<Training> getResults(ArrayList<ArrayList<String>> list){
		if(list == null) {
			return null;
		}
		ArrayList<Training> results = new ArrayList<>();		
		for (int i = 0; i < list.size(); i++) {
			ArrayList<String> indexedlist = list.get(i);
			results.add(new Training(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2)));
		}
		return FXCollections.observableArrayList(results);
	}

	private void initDrawer() {
		//drawer = new JFXDrawer();
		borderPane.setRight(null);
		drawer.setDirection(DrawerDirection.RIGHT);
		drawer.setDefaultDrawerSize(100);
		VBox toolbar;
		try {
			toolbar = FXMLLoader.load(getClass().getResource("TrainerToolbar.fxml"));
			drawer.setSidePane(toolbar);
			
			HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event e) -> {
					task.setRate(task.getRate() * -1);
					task.play();
					
					if (drawer.isShown()) {
						borderPane.setRight(null);
						drawer.close();
					}
					else {
						borderPane.setRight(drawer);
						drawer.open();;
						
					}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleCloseProgram() {
		((Stage) stackPane.getScene().getWindow()).close();
	}
	
	public static class Training{
		private final SimpleStringProperty trainingPlace;
		private final SimpleStringProperty trainingDate;
		private final SimpleStringProperty trainingTime;

		public Training(String trainingPlace, String time, String trainingDate) {
			this.trainingPlace = new SimpleStringProperty(trainingPlace);
			this.trainingDate = new SimpleStringProperty(trainingDate);
			this.trainingTime = new SimpleStringProperty(time);
 		}
				
		public String getTrainingPlace() {
			return trainingPlace.get();
		}
		public String getTrainingDate() {
			return trainingDate.get();
		}
		public String getTime() {
			return trainingTime.get();
		}
	}
	
	
}
