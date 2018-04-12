package tdt4140.gr1875.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.text.html.parser.Entity;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {

	static HttpServer server;

	public static void initialize() throws Exception {
		server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/training", new TrainingHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public static void tearDown() {
		server.stop(0);
	}

	public static List<String> getParams(HttpExchange ex) {
		Map<String, String> params = new HashMap<>();
		try {
			String bodyString = IOUtils.toString(ex.getRequestBody());
			List<String> args = Arrays.asList(bodyString.split("&"));
			return args;
		} catch (IOException e) {
			return new ArrayList<String>();
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
			List<String> args = getParams(ex);
			boolean added = false;
			try {
				if(args.get(0).equals("training")) {
					added = UseDB.addRow("training", args.get(1), args.get(2), args.get(3), args.get(4), args.get(5),args.get(6),args.get(7));
				}
				else if(args.get(0).equals("trainer")) {
					added = UseDB.addRow("training", args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
				}
				else if(args.get(0).equals("runner")) {
					added = UseDB.addRow("training", args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6), args.get(7));
				}
				else if(args.get(0).equals("result")) {
					added = UseDB.addRow("training", args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
				}
				else if(args.get(0).equals("login")) {
					added = UseDB.addRow("training", args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
				}
	
				sendResponse(ex, String.valueOf(added), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig klient-id", 400);
			}
		}

		private void get(HttpExchange ex) {
			// Faar en eksisterene klient. Kalles ved GET /exercise/session_id
			String sessionId = ex.getRequestURI().toString().split("/")[2];
			boolean added = false;			
			try {
				List<String> comparisons = Arrays.asList(sessionId.split("&"));
				ArrayList<ArrayList<String>> training = UseDB.getRow(comparisons.get(0),
						comparisons.subList(1, comparisons.size()));
				
				Gson gson = new Gson();
				String response = gson.toJson(training); // gson.tojson() converts your pojo to json
				response = changeNorwegianLetters(response);
				sendResponse(ex, response);
			} 
			catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig format paa okt-id", 400);
			} 
			catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
			}
		}
	}

	
	public static void main(String[] args) {
		HTTPServer s = new HTTPServer();
		try {
			s.initialize();
			
			String postUrl = "http://localhost:8000/training/login";
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet post = new HttpGet(postUrl);
			post.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(post);
			String ss = EntityUtils.toString(response.getEntity());
			
				Gson gson = new Gson();
				Type stringArr = new TypeToken<ArrayList<ArrayList<String>>>() {
				}.getType();
				ArrayList<ArrayList<String>> result = gson.fromJson(ss, stringArr);

			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			// fail();
		}
		finally {
			s.tearDown();
		}
	}
}

