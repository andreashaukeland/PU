package tdt4140.gr1875.app.core;

import org.junit.Test;
import static org.junit.Assert.*;
import junit.framework.Assert;


public class RunnerMainScreenTest {
	
	RunnerMainScreen rmc = new RunnerMainScreen();
	
	@Test
	public void testGetLastRun() {
		String res = rmc.getLastRun();
		
		Assert.assertNotNull(res);
	}

}
