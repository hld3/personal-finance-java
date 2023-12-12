package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.LoginDTOBuilder;
import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserProfileDTO;
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
		UserModel expectedUser = new UserModelBuilder().withPasswordHash(PasswordHashing.hashPassword(login.getPassword())).build();
		when(userRepository.retrievePasswordHashByEmail(login.getEmail())).thenReturn(expectedUser);

		UserProfileDTO result = userLoginService.confirmLogin(login);

		assertNotNull(result, "Profile data should be returned.");
		assertNotNull(result.getJWTToken(), "JWT token should be created");

		UserDTO userResult = result.getUserDTO();
		assertEquals(userResult.getUserId(), expectedUser.getUserId());
		assertEquals(userResult.getFirstName(), expectedUser.getFirstName());
		assertEquals(userResult.getLastName(), expectedUser.getLastName());
		assertEquals(userResult.getEmail(), expectedUser.getEmail());
		assertEquals(userResult.getPhone(), expectedUser.getPhone());
		assertEquals(userResult.getDateOfBirth(), expectedUser.getDateOfBirth());
		assertEquals(userResult.getCreationDate(), expectedUser.getCreationDate());
	}

	@Test
	public void test_invalidCredentials() {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel user = new UserModelBuilder().build();
		when(userRepository.retrievePasswordHashByEmail(login.getEmail())).thenReturn(user);

		UserProfileDTO result = userLoginService.confirmLogin(login);

		assertNull(result);
	}
}
