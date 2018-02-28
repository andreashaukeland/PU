package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class DbConnectionTest {

	@Test
	public void test() {
		DbConnection db = new DbConnection();
		String result = db.db_connect();
		Assert.assertEquals("ForrestGump", result);
	}

}
