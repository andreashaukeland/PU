package tdt4140.gr1875.app.ui;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class test_json_read {
	
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		
		try {
			JSONObject a = (JSONObject) parser.parse(new FileReader("/home/pederbg/Downloads/map.geojson"));
			System.out.println(a.toString());
			JSONArray feat = (JSONArray) a.get("features");
			JSONObject geom = (JSONObject) ((JSONObject) feat.get(0)).get("geometry");
			JSONArray coord = (JSONArray) geom.get("coordinates");
			System.out.println(coord.get(0));
			
			//JSONArray geom = (JSONArray) ((JSONObject) ((JSONObject) features.get(1)).get("geometry")).get("coordinates");
			
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		//JSONObject test = new JSONObject("{\"type\": \"FeatureCollection\"}");
		//System.out.println(String.valueOf(test));
	}

}
