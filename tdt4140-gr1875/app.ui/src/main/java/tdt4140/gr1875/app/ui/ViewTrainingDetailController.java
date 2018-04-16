package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
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
import netscape.javascript.JSObject;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.core.ViewTrainingDetail;
import tdt4140.gr1875.app.ui.TrainerMainScreenController.Results;

/*
 * This class is used to initialize the progress view for a runner. It initializes the map and charts for pulse and 
 * speed during the chosen run.
 * 
 */

public class ViewTrainingDetailController implements Initializable, MapComponentInitializedListener{
	private JFXDrawer drawer;
	@FXML private JFXHamburger hamburger;
	@FXML private StackPane stackPane;
	@FXML private BorderPane borderPane;
	@FXML private Tab pulseOverview;
	@FXML private Tab speedOverview;
	@FXML private Tab mapTab;
	
	@FXML private GoogleMapView googleMap;
	private GoogleMap map;
	List<Marker> markers = new ArrayList<>();
	PolylineOptions line_opt;
	Polyline line; 
	
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
		initChartTab();
		googleMap.addMapInializedListener(this);

					
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


	public void mapInitialized() {
		int runnerId;
        if(SessionInformation.userType.equals("runner")){
        	runnerId = SessionInformation.userId;
        }
        else {
        	runnerId = SessionInformation.currentRunnerViewed;
        }
        
		String geojsonFile = UseDB.getTable("Select map from result where trainingid="+SessionInformation.currentTrainingViewed+" and runnerid ="+runnerId).get(0).get(0);
	    
		if (geojsonFile.length() < 1) {
			//Set the initial properties of the map.
		    MapOptions mapOptions = new MapOptions();

		    mapOptions.center(new LatLong(63.4, 10.4))
		            .mapType(MapTypeIdEnum.ROADMAP)
		            .overviewMapControl(false)
		            .panControl(false)
		            .rotateControl(false)
		            .scaleControl(false)
		            .streetViewControl(false)
		            .zoomControl(false)
		            .mapMarker(true)
		            .zoom(3);

		    map = googleMap.createMap(mapOptions);
		    return;
		}
		 
	    LatLong[] coordinates = stringToCoordinates(geojsonFile);
	    
	    //Set the initial properties of the map.
	    MapOptions mapOptions = new MapOptions();

	    mapOptions.center(coordinates[0])
	            .mapType(MapTypeIdEnum.ROADMAP)
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .mapMarker(true)
	            .zoom(12);

	    map = googleMap.createMap(mapOptions);
	    
	    //Add a markers and draw runnerlines in map
	    
	    drawPath(coordinates, "#000000");
	    LatLong first = coordinates[0];
	    LatLong last = coordinates[coordinates.length - 1];
	    LatLong[] latlonglist = new LatLong[2];
	    latlonglist[0] = first;
	    latlonglist[1] = last;
	    drawPath(latlonglist, "#000000");
	    
	  //Add a markers and draw track coordinates in map
		String geojsonFileOfficialTrack = UseDB.getTable("Select track from training where trainingid=" + SessionInformation.currentTrainingViewed).get(0).get(0);
	    LatLong[] coordinatesOfficialTrack = stringToCoordinates(geojsonFileOfficialTrack);

	    setMultipleMarkers(coordinatesOfficialTrack);
	    drawPath(coordinatesOfficialTrack,  "#ff4500");
	    first = coordinatesOfficialTrack[0];
	    last = coordinatesOfficialTrack[coordinatesOfficialTrack.length - 1];
	    latlonglist = new LatLong[2];
	    latlonglist[0] = first;
	    latlonglist[1] = last;
	    drawPath(latlonglist, "#ff4500");
	}
	 
	
	// Take a string, converts it to geojson, parse it to get coordinates and returns a LatLong[] object.
	public LatLong[] stringToCoordinates(String track) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject geojson = (JSONObject) parser.parse(track);
			JSONArray features = (JSONArray) geojson.get("features");
			
			ArrayList<LatLong> coordinates = new ArrayList<>();
			Iterator<JSONObject> it = features.iterator();
	        
	        while (it.hasNext()) {
	            JSONObject feat = it.next();
				JSONObject geom = (JSONObject) feat.get("geometry");
				JSONArray cords = (JSONArray) geom.get("coordinates");
				coordinates.add( new LatLong((Double) cords.get(1), (Double) cords.get(0)) );
	        }
	        
	        LatLong[] result = coordinates.toArray(new LatLong[coordinates.size()]);
	        return result;
			
		} catch (ParseException e) {
			System.out.println("GoogleMapController.java: Error parsing geojson");
			return null;
		}		
	}
	
	public void drawPath(LatLong[] path, String color) {
		line_opt = new PolylineOptions();
		line_opt.path(new MVCArray(path))
	            .clickable(false)
	            .draggable(false)
	            .editable(false)
	            .strokeColor(color)
	            .strokeWeight(2)
	            .visible(true);

		line = new Polyline(line_opt);
		map.addMapShape(line);
		
	}
	
	private void setMultipleMarkers(LatLong[] coordinates) {
		for (int i = 0; i < coordinates.length; i++) {
			MarkerOptions options = new MarkerOptions();
			options.position(coordinates[i]).visible(Boolean.TRUE);
			Marker marker = new Marker(options);
			map.addMarker(marker);
		}
	}

	private void showMarker(double lat, double lng, String iconPath) {
	        MarkerOptions options = new MarkerOptions();
	        options.icon(iconPath).position(new LatLong(lat, lng));
	        Marker marker = new Marker(options);
	        markers.add(marker);
	        map.addMarker(marker);
	 }
	
	 @FXML
	 public void putMarker() {
		 map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
			 LatLong coordinates = new LatLong((JSObject) obj.getMember("latLng"));
			 showMarker(coordinates.getLatitude(), coordinates.getLongitude(), null);
		 });
	 }
	 public void clearMap() {
			map.clearMarkers();
			map.removeMapShape(line);
		}
		
		
	private void initChartTab() {
		final NumberAxis yAxis = new NumberAxis();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxisSpeed = new NumberAxis();
        final NumberAxis xAxisSpeed = new NumberAxis();
        xAxis.setLabel("Date:");
        final LineChart<Number,Number> lineChartSpeed = 
                new LineChart<Number,Number>(xAxisSpeed,yAxisSpeed);
        final LineChart<Number,Number> lineChartPulse = 
                new LineChart<Number,Number>(xAxis,yAxis);
        lineChartSpeed.setTitle("Progress of speed:");
        lineChartPulse.setTitle("Progress of pulse:");
        lineChartSpeed.setCreateSymbols(false);
        lineChartPulse.setCreateSymbols(false);
        XYChart.Series speedSeries = new XYChart.Series();
        XYChart.Series pulseSeries = new XYChart.Series();
        int runnerId;
        if(SessionInformation.userType.equals("runner")){
        	runnerId = SessionInformation.userId;
        }
        else {
        	runnerId = SessionInformation.currentRunnerViewed;
        }
        ArrayList<String> result = UseDB.getTable("Select time, comment, text from result where trainingid="+SessionInformation.currentTrainingViewed+" and runnerid ="+runnerId).get(0);
        String runnerData = result.get(2);
        for (String data : runnerData.split(";")) {
        	String[] dataArray = data.split(",");
        	double seconds = Double.valueOf(dataArray[0]);
			double pulse = Double.valueOf(dataArray[1]);
			double speed = Double.valueOf(dataArray[2]);
			speedSeries.getData().add(new XYChart.Data(seconds, speed));
			pulseSeries.getData().add(new XYChart.Data(seconds, pulse));
		}
        lineChartSpeed.getData().add(speedSeries);
        lineChartPulse.getData().add(pulseSeries);
        pulseOverview.setContent(lineChartPulse);
        speedOverview.setContent(lineChartSpeed);

	}

	
}
	
	

