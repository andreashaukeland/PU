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
import tdt4140.gr1875.app.ui.ViewTrainingsController.Training;


public class TrainerMainScreenController implements Initializable{

	@FXML private JFXHamburger hamburger;
	@FXML private StackPane stackPane;
	@FXML private BorderPane borderPane;
	@FXML private GridPane trainingMapTabGridPane;
	private JFXDrawer drawer = new JFXDrawer();
	
	@FXML private TableView<Results> tableView;
	@FXML private TableColumn<Training, String> nameColumn;
	@FXML private TableColumn<Training, String> trackColumn;
	@FXML private TableColumn<Training, String> timeColumn;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDrawer();
		initMap();
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT concat(firstname, ' ', lastname), place, result.time"
				+ " FROM result join runner on result.runnerid = runner.runnerid join training on training.trainingid ="
				+ "result.trainingid");
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
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		trackColumn.setCellValueFactory(new PropertyValueFactory<>("Track"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
	}
	
	private ObservableList<Results> getResults(ArrayList<ArrayList<String>> list){
		if(list == null) {
			return null;
		}
		ArrayList<Results> results = new ArrayList<>();		
		for (int i = 0; i < list.size(); i++) {
			ArrayList<String> indexedlist = list.get(i);
			results.add(new Results(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2)));
		}
		return FXCollections.observableArrayList(results);
	}

	private void initDrawer() {
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
	
	
	public static class Results{
		

		private final SimpleStringProperty name;
		private final SimpleStringProperty track;
		private final SimpleStringProperty time;
		
		public Results(String name, String track, String time) {
			this.name = new SimpleStringProperty(name);
			this.track = new SimpleStringProperty(track); 
			this.time = new SimpleStringProperty(time);
		}
		
		public String getName() {
			return name.get();
		}

		public String getTrack() {
			return track.get();
		}

		public String getTime() {
			return time.get();
		}
		
	}
	
	
}
