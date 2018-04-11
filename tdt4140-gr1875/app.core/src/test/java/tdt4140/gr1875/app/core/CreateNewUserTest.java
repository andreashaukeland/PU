package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class CreateNewUserTest {
	CreateNewUser testUser = new CreateNewUser();
	
	@Test
	public void checkAddNewUser() {
		String shortPhone = "" + 9090;
		String correctPhone = "" + 90909090;
		try {
			testUser.addNewUser("Torgeir95", "MANUERBEST", "Torgeir", "Mourinho", "fotballgutt@hotmail.com", correctPhone, "1995-11-11", true, false);
			testUser.addNewUser("TorgeirTrener", "MANUERBEST", "Torgeir", "Mourinho", "fotballgutt@hotmail.com", correctPhone, "1995-11-11", false, true);
			testUser.addNewUser("Torgeir98", "MANUERBEST", "Torgeir", "Mourinho", "fotballgutt@hotmail.com", shortPhone, "1995-11-11", true, false);
		
		}
		catch(Exception e){
			Assert.assertEquals("Not Valid Mobile Number \n", e.getMessage());
		}
		finally {
			UseDB.deleteUserByUsername("Torgeir95");
			UseDB.deleteUserByUsername("Torgeir98");
			UseDB.deleteUserByUsername("TorgeirTrener");
		}
	}
	
	@Test
	public void checkMobileTest() {
		boolean correct =testUser.checkMobile("12345678");
		boolean tooShort = testUser.checkMobile("123");
		boolean tooLong = testUser.checkMobile("123456789098765");
		boolean containsChars = testUser.checkMobile("123erfghui9876tghj");
		Assert.assertEquals(true, correct);
		Assert.assertEquals(false, tooShort);
		Assert.assertEquals(false, tooLong);
		Assert.assertEquals(false, containsChars);
	}
	@Test
	public void checkEmailTest() {
		boolean correct = testUser.checkEmail("test@test.com");
		boolean noService = testUser.checkEmail("test@.com");
		boolean noName = testUser.checkEmail("@test.com");
		boolean noDomain = testUser.checkEmail("test@test");
		boolean toManyAlfa = testUser.checkEmail("test@@test@.com");
		Assert.assertEquals(true, correct);
		Assert.assertEquals(false, noService);
		Assert.assertEquals(false, noName);
		Assert.assertEquals(false, noDomain);
	}
	@Test
	public void checkBirthDayTest() {
		boolean correct = testUser.checkBirthDay("1995-11-15");
		boolean wrongFormat = testUser.checkBirthDay("15111995");
		boolean checkfeb = testUser.checkBirthDay("1234-02-31");
		boolean unvalidMonth = testUser.checkBirthDay("1234-20-11");
		boolean unvalidDay = testUser.checkBirthDay("2015-11-40");
		Assert.assertEquals(true, correct);
		Assert.assertEquals(false, wrongFormat);
		Assert.assertEquals(false, checkfeb);
		Assert.assertEquals(false, unvalidMonth);
		Assert.assertEquals(false, unvalidDay);
	}
	
	@Test
	public void deleteUserTest() {
		String username = "TestUser";
		testUser.addNewUser(username, "Test", "Test", "Test", "test@testmail.test", "12345678", "1995-11-11", false, true);
		int id = Integer.parseInt(UseDB.getIDByName("runner", "firstname=Test", "lastname=Test").get(0).get(0));
		boolean deleted = testUser.deleteUser(username, id, "runner");
		Assert.assertEquals(true, deleted);
	}
}
