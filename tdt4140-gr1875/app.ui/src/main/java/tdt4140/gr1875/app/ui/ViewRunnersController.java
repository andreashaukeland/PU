package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.ui.ViewTrainingsController.Training;

/*
 * This class is used to initialize the view athletes window and load all athletes in a table.You can also choose an 
 * athlete and view the progress for that athlete.  
 * 
 */

public class ViewRunnersController implements Initializable{ 

	@FXML private TableView<Athlete> tableView;
	@FXML private TableColumn<Athlete, String> FirstNameColumn;
	@FXML private TableColumn<Athlete, String> LastNameColumn;
	@FXML private TableColumn<Athlete, String> BirthDayColumn;
	@FXML private JFXButton chooseUserButton;
    @FXML private JFXButton backButton;
    

    @FXML
    void OnBackButton(ActionEvent event) {
    	System.out.println(SessionInformation.userType);
    	if (SessionInformation.userType.equals("trainer")) {
    		SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) tableView, this);
    	}
    	
    	if (SessionInformation.userType.equals("runner")) {
    		SceneLoader.loadWindow("RunnerMainScreen.fxml", (Node) tableView, this);
    	}
    }

    @FXML
    void OnChooseUser(ActionEvent event) {
    	Athlete selectedAthlete = tableView.getSelectionModel().getSelectedItem();
    	int currentRunnerId = selectedAthlete.getRunnerId();
    	SessionInformation.currentRunnerViewed = currentRunnerId;
    	SceneLoader.loadWindow("RunnerProgressScreen.fxml", (Node) tableView, this);
    	
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT firstname, lastname, birthday, runnerid FROM runner");
		tableView.getItems().setAll(getRes(list));
	}
	
	private void initCol() {
		FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
		LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
		BirthDayColumn.setCellValueFactory(new PropertyValueFactory<>("BirthDay"));
	}
	
	private ObservableList<Athlete> getRes(ArrayList<ArrayList<String>> list){
		ArrayList<Athlete> res = new ArrayList<>();		
		for (int i = 0; i < list.size(); i++) {
			ArrayList<String> indexedlist = list.get(i);
			res.add(new Athlete(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2),indexedlist.get(3)));
		}
		return FXCollections.observableArrayList(res);
	}

	
	public static class Athlete{

		private final SimpleStringProperty FirstName;
		private final SimpleStringProperty LastName;
		private final SimpleStringProperty BirthDay;
		private final int runnerID;
		
		public Athlete(String name, String track, String time, String runnerID) {
			this.FirstName = new SimpleStringProperty(name);
			this.LastName = new SimpleStringProperty(track); 
			this.BirthDay = new SimpleStringProperty(time);
			this.runnerID = Integer.valueOf(runnerID);
		}
		
		public String getFirstName() {
			return FirstName.get();
		}

		public String getLastName() {
			return LastName.get();
		}

		public String getBirthDay() {
			return BirthDay.get();
		}
		
		public int getRunnerId() {
			return runnerID;
		}
		
	}
	

}
