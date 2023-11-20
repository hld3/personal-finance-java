package com.dodson.personalfinance.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PasswordHashingUnitTest {

	@Test
	void test_passwordHashingConsistency() throws Exception {
		String password = "MySuperPassword";
		String hashedPassword1 = PasswordHashing.hashPassword(password);
		String hashedPassword2 = PasswordHashing.hashPassword(password);
		assertEquals(hashedPassword1, hashedPassword2);
	}

	@Test
	void testHashPasswordDifferentInputs() throws Exception {
		String password1 = "CrazyDifficultPassword";
		String password2 = "MySuperEasyPassword";

		String hashedPassword1 = PasswordHashing.hashPassword(password1);
		String hashedPassword2 = PasswordHashing.hashPassword(password2);

		assertNotEquals(hashedPassword1, hashedPassword2);
	}
}
