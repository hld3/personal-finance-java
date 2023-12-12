package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.LoginDTOBuilder;
import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserProfileDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.PasswordHashing;

@SpringBootTest
@ActiveProfiles("h2")
public class UserLoginServiceIntegrationTest {

	@Autowired
	private UserLoginService userLoginService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void test_whenValidUserLogsIn_returnToken() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel expectedUser = new UserModelBuilder().build();
		UserModel noise = new UserModelBuilder().build();
		expectedUser.setPasswordHash(PasswordHashing.hashPassword(login.getPassword()));
		expectedUser.setEmail(login.getEmail());
		userRepository.saveAllAndFlush(List.of(expectedUser, noise));

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
	void test_whenInvalidUserLogsIn_returnNull() {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel noise = new UserModelBuilder().build();
		userRepository.saveAndFlush(noise);

		UserProfileDTO result = userLoginService.confirmLogin(login);

		assertNull(result);
	}
}
