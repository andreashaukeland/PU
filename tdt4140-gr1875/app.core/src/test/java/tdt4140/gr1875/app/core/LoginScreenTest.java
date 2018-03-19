package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class LoginScreenTest {

	@Test
	public void test() {
		LoginScreen login = new LoginScreen();
		Assert.assertEquals(true, login.checkUsernameAndPassword("runner", "test"));
		Assert.assertEquals(false, login.checkUsernameAndPassword("", ""));
	}

}
