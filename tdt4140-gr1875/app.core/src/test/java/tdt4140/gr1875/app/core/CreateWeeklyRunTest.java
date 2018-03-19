package tdt4140.gr1875.app.core;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1875.app.core.CreateWeeklyRun;

@SuppressWarnings("deprecation")
public class CreateWeeklyRunTest {
	
	//Database test will not work without VPN
	
	@Test
	public void SubmitTest() {
		
		CreateWeeklyRun run = new CreateWeeklyRun();
		Assert.assertEquals(true, run.submit("TestRun", "2018.03.09", "10:00"));
		UseDB.deleteRow("training", Integer.parseInt(UseDB.getIDByName("training", "place=TestRun").get(0).get(0)));
	}
	

}
