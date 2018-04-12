package tdt4140.gr1875.web.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {

	static HttpServer server;

	public static void initialize() throws Exception {
		server = HttpServer.create(new InetSocketAddress(3006), 0);
		server.createContext("/training", new TrainingHandler());
		//server.createContext("/clients", new ClientHandler());
		//server.createContext("/exercise", new ExerciseHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public static void tearDown() {
		server.stop(0);
	}

	public static Map<String, String> getParams(HttpExchange ex) {
		Map<String, String> params = new HashMap<>();
		try {
			String bodyString = IOUtils.toString(ex.getRequestBody());
			for (String s : bodyString.split("&")) {
				String[] tokens = s.split("=");
				params.put(tokens[0], tokens[1]);
			}
			return params;
		} catch (IOException e) {
			return new HashMap<String, String>();
		}
	}

	public static void sendResponse(HttpExchange ex, String response, int statusCode) {
		try {
			ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
			ex.sendResponseHeaders(statusCode, response.length());
			OutputStream os = ex.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (IOException e) {
			return;
		}
	}

	public static void sendResponse(HttpExchange ex, String response) {
		sendResponse(ex, response, 200);
	}

	private static String changeNorwegianLetters(String response) {
		return response.replaceAll("\u00e6", "ae").replaceAll("\u00f8", "o").replaceAll("\u00e5", "aa")
				.replaceAll("\u00c6", "AE").replaceAll("\u00d8", "O").replaceAll("\u00c5", "AA");
	}

	static class TrainingHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange ex) {
			if (ex.getRequestMethod().equals("GET")) {
				get(ex);
			} else if (ex.getRequestMethod().equals("POST")) {
				post(ex);
			}
		}

		private void post(HttpExchange ex) {
			// Lager en ny session. Kalles om man faar en http request POST /session
			Map<String, String> params = getParams(ex);
			try {
				boolean trainingAdded = UseDB.submitTimeToTraining( Integer.parseInt(params.get("runnerid")), params.get("time"), params.get("comment"));
				sendResponse(ex, String.valueOf(trainingAdded), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig klient-id", 400);
			}
		}

		private void get(HttpExchange ex) {
			// Faar en eksisterene klient. Kalles ved GET /exercise/session_id
			String sessionId = ex.getRequestURI().toString().split("/")[2];
			try {
				ArrayList<ArrayList<String>> training = UseDB.getIDByName("training", "trainingid="+sessionId);
				Gson gson = new Gson();
				String response = gson.toJson(training.get(0)); // gson.tojson() converts your pojo to json
				response = changeNorwegianLetters(response);
				sendResponse(ex, response);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig format paa okt-id", 400);
			} catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
		}
	}

	}
	
	public static void main(String[] args) {
		HTTPServer s = new HTTPServer();
		try {
			s.initialize();
			
			String postUrl = "http://localhost:3006/training";
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(postUrl);
			StringEntity postingString = new StringEntity("runnerid=5&time=01:00:00&comment=server&text='mongo'", "utf-8");
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(post);
			
			 s.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
			// fail();
		}
	}
}

