package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.ui.TrainerMainScreenController.Results;

public class ViewTrainingsController implements Initializable{

	@FXML private TableView<Training> tableView;
	@FXML private TableColumn<Results, String> trainingPlaceColumn;
	@FXML private TableColumn<Results, String> trainingTimeColumn;
	@FXML private TableColumn<Results, String> trainingDateColumn;
	@FXML private TableColumn<Results, String> trainingDistanceColumn;
	@FXML private JFXButton toggleButton;
    @FXML private JFXButton backButton;
    
    
    @FXML
    void OnBackButton(ActionEvent event) {
    	if (SessionInformation.userType.equals("trainer")) {
    		SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) tableView, this);
    	}
    	
    	if (SessionInformation.userType.equals("runner")) {
    		SceneLoader.loadWindow("RunnerMainScreen.fxml", (Node) tableView, this);
    	}
    }
    
    @FXML
	public void onLoadTrack() {
		Training obj = tableView.getSelectionModel().getSelectedItem();
		if (obj != null) {
			String trainingPlace = obj.getTrainingPlace();
			SessionInformation.currentTrackLoaded = UseDB.getTrackIDFromPlace(trainingPlace);
			createAlert("Successfully loaded " + trainingPlace + ".");
			SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) tableView, this);
		}
		
	}
    
    private final void createAlert(String string) {
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(string);
		alert.showAndWait();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("select place, time, date, distance from training where "
				+ "officialTraining = 'yes'");
		tableView.getItems().setAll(getRes(list));
	}
	
	private void initCol() {		
		trainingPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingPlace"));
		trainingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
		trainingDateColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingDate"));
		trainingDistanceColumn.setCellValueFactory(new PropertyValueFactory<>("Distance"));
	}
	
	private ObservableList<Training> getRes(ArrayList<ArrayList<String>> list){
		ArrayList<Training> res = new ArrayList<>();		
		for (int i = 0; i < list.size(); i++) {
			ArrayList<String> indexedlist = list.get(i);
			res.add(new Training(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2), indexedlist.get(3)));
		}
		return FXCollections.observableArrayList(res);
	}

	
	public static class Training{
		private final SimpleStringProperty trainingPlace;
		private final SimpleStringProperty trainingDate;
		private final SimpleStringProperty trainingTime;
		private final SimpleStringProperty trainingDistance;

		public Training(String trainingPlace, String time, String trainingDate, String trainingDistance) {
			this.trainingPlace = new SimpleStringProperty(trainingPlace);
			this.trainingDate = new SimpleStringProperty(trainingDate);
			this.trainingTime = new SimpleStringProperty(time);
			this.trainingDistance = new SimpleStringProperty(trainingDistance);
 		}
		public String getTrainingPlace() {return trainingPlace.get();}
		public String getTrainingDate() {return trainingDate.get();}
		public String getTime() {return trainingTime.get();}
		public String getDistance() {return trainingDistance.get();}
	}
	
}
