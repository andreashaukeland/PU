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

public class ViewResultsController implements Initializable{

	
	@FXML
    private TableView<Results> tableView;
	
	@FXML
    private TableColumn<Results, String> nameColumn;
	
	@FXML
    private TableColumn<Results, String> trackColumn;
	
	@FXML
    private TableColumn<Results, String> timeColumn;
	
	
	@FXML
    private JFXButton toggleButton;

    @FXML
    private JFXButton backButton;
    
    
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
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT concat(firstname, ' ', lastname), trainingid, time"
				+ " FROM result join runner on result.runnerid = runner.runnerid");
		tableView.getItems().setAll(getRes(list));
	}
	
	private void initCol() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		trackColumn.setCellValueFactory(new PropertyValueFactory<>("Track"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
	}
	
	private ObservableList<Results> getRes(ArrayList<ArrayList<String>> list){
		ArrayList<Results> res = new ArrayList<>();		
		for (int i = 0; i < list.size(); i++) {
			ArrayList<String> indexedlist = list.get(i);
			res.add(new Results(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2)));
		}
		return FXCollections.observableArrayList(res);
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
	
	@FXML
    void OnToggleView(ActionEvent event) {

    }
	
}
