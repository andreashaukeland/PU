package tdt4140.gr1875.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class CreateNewUserTest {
	CreateNewUser testUser = new CreateNewUser();
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
	public void validBirthDayTest() {
		boolean correct = testUser.validBirthDay("1995-11-15");
		boolean wrongFormat = testUser.validBirthDay("15111995");
		boolean checkfeb = testUser.validBirthDay("1234-02-31");
		boolean unvalidMonth = testUser.validBirthDay("1234-20-11");
		boolean unvalidDay = testUser.validBirthDay("2015-11-40");
		Assert.assertEquals(true, correct);
		Assert.assertEquals(false, wrongFormat);
		Assert.assertEquals(false, checkfeb);
		Assert.assertEquals(false, unvalidMonth);
		Assert.assertEquals(false, unvalidDay);
		
		
	}
}
