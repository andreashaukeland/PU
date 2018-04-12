package tdt4140.gr1875.app.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;


public class GoogleMapController implements Initializable, MapComponentInitializedListener{

	@FXML private GoogleMapView googleMap;
	private GoogleMap map;
	List<Marker> markers = new ArrayList<>();
	PolylineOptions line_opt;
	Polyline line; 
	
	
	@Override
	public void mapInitialized() {
		String geojsonFile = UseDB.getGeojsonTrack(SessionInformation.currentTrackLoaded); //TODO to see in map based on which track the trainer has chosen in trainings tab
	    
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
	    
	    //Add a markers and draw lines in map
	    setMultipleMarkers(coordinates);
	    drawPath(coordinates);
	    LatLong first = coordinates[0];
	    LatLong last = coordinates[coordinates.length - 1];
	    LatLong[] latlonglist = new LatLong[2];
	    latlonglist[0] = first;
	    latlonglist[1] = last;
	    drawPath(latlonglist);
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
	
	public void clearMap() {
		map.clearMarkers();
		map.removeMapShape(line);
	}
	
	
}
