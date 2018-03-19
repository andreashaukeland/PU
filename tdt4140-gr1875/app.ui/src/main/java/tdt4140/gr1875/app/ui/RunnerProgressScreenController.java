package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tdt4140.gr1875.app.core.RunnerMainScreen;
import tdt4140.gr1875.app.core.RunnerProgressScreen;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.ui.ViewResultsController.Results;

public class RunnerProgressScreenController implements Initializable{

	private JFXDrawer drawer;
	@FXML private JFXHamburger hamburger;
	@FXML private StackPane stackPane;
	@FXML private BorderPane borderPane;

	@FXML private TableView<Results> tableView;
	@FXML private TableColumn<Results, String> trainingNumberColumn;
	@FXML private TableColumn<Results, String> trainingDateColumn;
	@FXML private TableColumn<Results, String> trainingPlaceColumn;
	@FXML private TableColumn<Results, String> distanceColumn;
	@FXML private TableColumn<Results, String> timeColumn;
	@FXML private JFXButton toggleButton;
	@FXML private JFXButton backButton;
	@FXML private Tab progressTab;
	
	private RunnerProgressScreen runnerProgressScreen = new RunnerProgressScreen();
	private int currentUser;
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//If a trainer is logged in, the current runner we want to view is currentRunnerViewed
		//Else we want to watch the runner that is logged in
		if(SessionInformation.userType.equals("trainer")) {
			currentUser = SessionInformation.currentRunnerViewed;
		}
		else {
			currentUser = SessionInformation.userId;
		}
		initDrawer();
		initCol();
		ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT training.trainingid, place, date, distance, result.time"
				+ " FROM training join result on result.trainingid = training.trainingid join runner on runner.runnerid = result.runnerid"
				+ " WHERE runner.runnerid =" + currentUser +";");
		tableView.getItems().setAll(getResults(list));
		initProgressChart();
	}
	
	private void initProgressChart() {
		final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date:");
        final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
        lineChart.setTitle("Progress of speed on runs:");
        XYChart.Series series = new XYChart.Series();
        ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT date, distance, result.time"
				+ " FROM training join result on result.trainingid = training.trainingid join runner on runner.runnerid = result.runnerid"
				+ " WHERE runner.runnerid =" + currentUser + ";");
        for (ArrayList<String> result: list) {
        	Time time = Time.valueOf(result.get(2));
			double hours = time.getHours();
			double minutes = time.getMinutes();
			double seconds = time.getSeconds();
			double totalHours = hours + minutes / 60 + seconds / 3600;
			
			double distance = Double.valueOf(result.get(1));
			double speed = distance / totalHours;
			String date = result.get(0);
			series.getData().add(new XYChart.Data(date, speed));
		}
        lineChart.getData().add(series);
        progressTab.setContent(lineChart);
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
	
	@FXML
	public void handleCloseProgram() {
		((Stage) stackPane.getScene().getWindow()).close();
	}
	
	@FXML
    void OnBackButton(ActionEvent event) {
    	if (SessionInformation.userType.equals("trainer")) {
    		SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) tableView, this);
    	}
    	
    	if (SessionInformation.userType.equals("runner")) {
    		SceneLoader.loadWindow("RunnerMainScreen.fxml", (Node) tableView, this);
    	}
    }
	private void initCol() {
		trainingNumberColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingNumber"));
		trainingPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingPlace"));
		trainingDateColumn.setCellValueFactory(new PropertyValueFactory<>("TrainingDate"));
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("Distance"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
	}
	
	private ObservableList<Results> getResults(ArrayList<ArrayList<String>> list){
		if(list == null) {
			return null;
		}
		ArrayList<Results> results = new ArrayList<>();		
		for (int i = 0; i < list.size(); i++) {
			ArrayList<String> indexedlist = list.get(i);
			results.add(new Results(indexedlist.get(0), indexedlist.get(1), indexedlist.get(2),indexedlist.get(3),indexedlist.get(4)));
		}
		return FXCollections.observableArrayList(results);
	}

	
	public static class Results{
		private final SimpleStringProperty trainingNumber;
		private final SimpleStringProperty trainingPlace;
		private final SimpleStringProperty trainingDate;
		private final SimpleStringProperty time;
		private final SimpleStringProperty distance;

		
		public Results(String trainingNumber, String trainingPlace, String trainingDate, String distance, String time) {
			this.trainingNumber = new SimpleStringProperty(trainingNumber);
			this.trainingPlace = new SimpleStringProperty(trainingPlace);
			this.trainingDate = new SimpleStringProperty(trainingDate);
			this.time = new SimpleStringProperty(time);
			this.distance = new SimpleStringProperty(distance); 
		}
		
		public String getTrainingNumber() {
			return trainingNumber.get();
		}
		public String getTrainingPlace() {
			return trainingPlace.get();
		}
		public String getTrainingDate() {
			return trainingDate.get();
		}

		public String getDistance() {
			return distance.get();
		}

		public String getTime() {
			return time.get();
		}
	}

}
