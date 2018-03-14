package tdt4140.gr1875.app.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import netscape.javascript.JSObject;

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
	            .zoom(12);

	    map = googleMap.createMap(mapOptions);

	    //Add a marker to the map
	    MarkerOptions markerOptions = new MarkerOptions();

	    markerOptions.position(new LatLong(62.185777, 6.106872) )
	                .visible(Boolean.TRUE)
	                .title("My Marker");
	    Marker marker = new Marker(markerOptions);
	    map.addMarker(marker);
	    drawPath(null);
	}
	
	public void drawPath(LatLong[] path) {
		LatLong[] TestPath = {new LatLong(-13.163068, -72.545128),new LatLong(48.858093, 2.294694),new LatLong(27.987850,86.925026),new LatLong(-25.344490, 131.035431), new LatLong(-33.918861, 18.423300), new LatLong(-13.163068, -72.545128)};
		line_opt = new PolylineOptions();
		line_opt.path(new MVCArray(TestPath))
	            .clickable(false)
	            .draggable(false)
	            .editable(false)
	            .strokeColor("#ff4500")
	            .strokeWeight(2)
	            .visible(true);

	line = new Polyline(line_opt);
	map.addMapShape(line);
	}

	public void showMarker(double lat, double lng, String iconPath) {
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
