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
import tdt4140.gr1875.app.ui.ViewResultsController.Results;

public class ViewRunnersController implements Initializable{ //implements ToolbarListener

	@FXML
    private TableView<Athlete> tableView;
	
	@FXML
    private TableColumn<Athlete, String> FirstNameColumn;
	
	@FXML
    private TableColumn<Athlete, String> LastNameColumn;
	
	@FXML
    private TableColumn<Athlete, String> BirthDayColumn;

    @FXML
    private JFXButton toggleViewButton;

    @FXML
    private JFXButton backButton;
    

    @FXML
    void OnBackButton(ActionEvent event) {
    	System.out.println(SessionInformation.userType);
    	if (SessionInformation.userType.equals("trainer")) {
    		loadWindow("TrainerMainScreen.fxml", (Node) tableView);
    	}
    	
    	if (SessionInformation.userType.equals("runner")) {
    		loadWindow("RunnerMainScreen.fxml", (Node) tableView);
    	}
    }
    
    private void loadWindow(String loc, Node root) {
		try {
    		Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Scene newScene = new Scene(parent);
	    	Stage curStage = (Stage) root.getScene().getWindow();
	    	curStage.setScene(newScene);
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void OnToggleButton(ActionEvent event) {

    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT firstname, lastname, birthday FROM runner");
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
			res.add(new Athlete(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2)));
		}
		return FXCollections.observableArrayList(res);
	}

	
	public static class Athlete{
		

		private final SimpleStringProperty FirstName;
		private final SimpleStringProperty LastName;
		private final SimpleStringProperty BirthDay;
		
		public Athlete(String name, String track, String time) {
			this.FirstName = new SimpleStringProperty(name);
			this.LastName = new SimpleStringProperty(track); 
			this.BirthDay = new SimpleStringProperty(time);
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
		
	}
	

}
