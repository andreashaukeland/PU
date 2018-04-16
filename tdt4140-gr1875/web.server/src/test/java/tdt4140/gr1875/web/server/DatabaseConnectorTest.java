package tdt4140.gr1875.web.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.jws.soap.SOAPBinding.Use;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class DatabaseConnectorTest {
	
	@Test
	public void DBTest() {
		DatabaseConnector.addRow("runner", "99", "Test", "Testsen", "1995-01-01",
				"test@testing.com", "12345678", "test");
		ArrayList<ArrayList<String>> result = DatabaseConnector.getRow("runner", Arrays.asList("runnerid=99"));
		Assert.assertTrue(result.get(0).get(1).equals("Test"));
	}	
}


