package tdt4140.gr1875.app.core;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1875.app.core.CreateWeeklyRun;

@SuppressWarnings("deprecation")
public class CreateWeeklyRunTest {

	
	@Test
	public void test() {
		CreateWeeklyRun run = new CreateWeeklyRun();
		Assert.assertEquals(true, run.submit("run", "2018.03.09"));
	}

}
