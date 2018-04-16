package tdt4140.gr1875.web.server;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.impl.bootstrap.HttpServer;
import org.junit.Test;

import junit.framework.Assert;

public class HTTPServerTest {
	
	@Test
	public void ServerTest() {
		HTTPServer s = new HTTPServer();
		boolean error = true;
		try {
			s.initialize();
			s.tearDown();
			error = false;
		}
		catch (Exception e) {
			error = true;
		}
		finally {
			Assert.assertFalse(error);
		}
	}	

}
