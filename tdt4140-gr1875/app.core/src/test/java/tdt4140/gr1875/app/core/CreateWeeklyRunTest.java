package tdt4140.gr1875.app.core;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1875.app.core.CreateWeeklyRun;

@SuppressWarnings("deprecation")
public class CreateWeeklyRunTest {

	//Database test will not work without VPN
	CreateWeeklyRun run = new CreateWeeklyRun();

	@Test
	public void SubmitTest() {
		CreateWeeklyRun run = new CreateWeeklyRun();
		Assert.assertEquals(true, run.submit("TestRun", "2018.03.09", "10:00", 1, ""));
		Assert.assertEquals(false, run.submit("TestRun", "2018.03.09", "10:00", 1, "not_valid_json_file"));
		UseDB.deleteRow("training", Integer.parseInt(UseDB.getIDByName("training", "place=TestRun").get(0).get(0)));
	}

	@Test
	public void checkValidDateTest() {
		boolean lenErr = run.checkValidDate("not_lenght_of_10");
		boolean parseErr = run.checkValidDate("201a-0v-0s");

		Assert.assertEquals(false, lenErr);
		Assert.assertEquals(false, parseErr);
	}

	@Test
	public void checkValidTimeTest() {
		boolean lenErr = run.checkValidDate("not_lenght_of_5");
		boolean parseErr = run.checkValidDate("1a:0v:02");

		Assert.assertEquals(false, lenErr);
		Assert.assertEquals(false, parseErr);
	}

	@Test
	public void checkValidPlaceTest() {
		boolean lenErr = run.checkValidDate("");

		Assert.assertEquals(false, lenErr);
	}


}
