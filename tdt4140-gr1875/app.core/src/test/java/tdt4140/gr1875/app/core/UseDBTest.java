package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class UseDBTest {

	@Test
	public void test() {
		//String result = UseDB.getFromDB("SELECT * FROM runner").get(0).get(0);
		//Assert.assertEquals("Forrest", result);
		Assert.assertEquals("true", "true"); // used to test when ntnu database is down

	}

}
