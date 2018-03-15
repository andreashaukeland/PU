package tdt4140.gr1875.app.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.ISO8601Utils;
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
import com.lynden.gmapsfx.service.geocoding.GeocoderGeometry;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import tdt4140.gr1875.app.core.UseDB;


public class GoogleMapController implements Initializable, MapComponentInitializedListener{

	@FXML private GoogleMapView googleMap;
	private GoogleMap map;
	List<Marker> markers = new ArrayList<>();
	PolylineOptions line_opt;
	Polyline line; 
	
	
	
	@Override
	public void mapInitialized() {
		//Set the initial properties of the map.
	    MapOptions mapOptions = new MapOptions();

	    mapOptions.center(new LatLong(62.185777, 6.106872))
	            .mapType(MapTypeIdEnum.ROADMAP)
	            .overviewMapControl(false)
	            .panControl(false)
	            .rotateControl(false)
	            .scaleControl(false)
	            .streetViewControl(false)
	            .zoomControl(false)
	            .mapMarker(true)
	            .zoom(8);

	    map = googleMap.createMap(mapOptions);
	    
	    String geojsonFile = UseDB.getGeojsonTrack(2); //TODO make method that returns value of which track you want 
	    //to see in map based on which track the trainer has chosen in trainings tab 
	    LatLong[] coordinates = stringToCoordinates(geojsonFile);
	    
	    //Add a markers to the map
	    setMultipleMarkers(coordinates);
	    drawPath(coordinates);
	}
	
	public void drawPath(LatLong[] path) {
		line_opt = new PolylineOptions();
		line_opt.path(new MVCArray(path))
	            .clickable(false)
	            .draggable(false)
	            .editable(false)
	            .strokeColor("#ff4500")
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
	 
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		googleMap.addMapInializedListener(this);
		
	}
	
	
}
