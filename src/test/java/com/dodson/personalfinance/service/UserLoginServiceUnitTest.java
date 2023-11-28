package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.LoginDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.PasswordHashing;

public class UserLoginServiceUnitTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserLoginService userLoginService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void test_loginSuccessful() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel user = new UserModelBuilder().withPasswordHash(PasswordHashing.hashPassword(login.getPassword())).build();
		when(userRepository.retrievePasswordHashByEmail(login.getEmail())).thenReturn(user);

		String result = userLoginService.confirmLogin(login);

		assertNotNull(result, "Password hashes should match");
	}

	@Test
	public void test_invalidCredentials() {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel user = new UserModelBuilder().build();
		when(userRepository.retrievePasswordHashByEmail(login.getEmail())).thenReturn(user);

		String result = userLoginService.confirmLogin(login);

		assertNull(result);
	}
}
