package tdt4140.gr1875.app.core;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class HTTPServerTest {

	public static final int BAD_REQUEST = 400;
	public static final int NOT_FOUND = 404;
	public static final int CREATED = 201;
	public static final int OK = 200;

	@Before
	public void setUpServer() {
		try {
			HTTPServer.initialize();
		} catch (Exception e) {
			e.printStackTrace();
			// fail();
		}
	}

	@Test
	public void testCreateSession() throws ClientProtocolException, IOException {
		String postUrl = "http://localhost:8000/training";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("runnerid=1&time=10:00:00&comment=server&text=''", "utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		String s = EntityUtils.toString(response.getEntity());

		assertTrue(Integer.parseInt(s) > 0);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);

	}
/*
	@Test
	public void testCreateSessionWrongClientId() throws ClientProtocolException, IOException {
		String postUrl = "http://localhost:8000/session";
		Gson gson = new Gson();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=kevin&date=2017-10-10&note=me", "utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}

	@Test
	public void testGetSession() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/session/5";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
		String s = EntityUtils.toString(response.getEntity());
		assertEquals(5, Integer.parseInt(extractInfoFromResponse(s).get("id")));
	}

	@Test
	public void testGetSessionWithNonexcistingID() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/session/750";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}

	// Hittil er det lovlig aa legge til negative id'er i databasen, men det er ikke
	// "lov"..
	@Test
	public void testGetSessionWithIllegalID() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/session/-69";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}

	@Test
	public void testCreateClient() throws ClientProtocolException, IOException {
		String postUrl = "http://localhost:8000/clients";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(
				"name=Lise&phone=12345678&age=17&motivation=sugardaddy&trainerID=2", "utf-8");

		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		String s = EntityUtils.toString(response.getEntity());
		assertTrue(Integer.parseInt(s) > 0);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
	}

	@Test
	public void testGetClient() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/clients/5";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);

		String s = EntityUtils.toString(response.getEntity());
		assertEquals(5, Integer.parseInt(extractInfoFromResponse(s).get("id")));
	}

	@Test
	public void testGetClientWithNonexistingID() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/clients/12000000";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}

	@Test
	public void testGetClientWithIllegalID() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/clients/seks";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}

	@Test
	public void testPutClient() throws ClientProtocolException, IOException {
		String putUrl = "http://localhost:8000/clients/55";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(putUrl);
		StringEntity puttingString = new StringEntity(
				"name=Lise&phone=12345678&age=17&motivation=sugardaddy&trainerID=2", "utf-8");

		put.setEntity(puttingString);
		put.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(put);

		String s = EntityUtils.toString(response.getEntity());
		assertTrue(Integer.parseInt(s) > 0);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
	}

	@Test
	public void testCreateStrengthExercise() throws ClientProtocolException, IOException {
		String postUrl = "http://localhost:8000/exercise";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("reps=4&sett=3&weight=17&note=dritbra&sessionId=1&exerciseId=6",
				"utf-8");

		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		String s = EntityUtils.toString(response.getEntity());
		System.out.println(s);
		assertTrue(Integer.parseInt(s) > 0);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
	}

	@Test
	public void testGetExercises() throws ClientProtocolException, IOException {
		String getUrl = "http://localhost:8000/exercise/5";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
	}
*/

	// Konverterer fra Json til map, der nokkel er variabelnavn
	private Map<String, String> extractInfoFromResponse(String json) {
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = gson.fromJson(json, stringStringMap);
		return map;
	}

	@After
	public void tearDownServer() {
		HTTPServer.tearDown();
	}
}