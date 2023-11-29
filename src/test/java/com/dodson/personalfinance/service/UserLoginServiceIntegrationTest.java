package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.dodson.personalfinance.utility.PasswordHashing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.LoginDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;

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
		UserModel user = new UserModelBuilder().build();
		UserModel noise = new UserModelBuilder().build();
		user.setPasswordHash(PasswordHashing.hashPassword(login.getPassword()));
		user.setEmail(login.getEmail());
		userRepository.saveAllAndFlush(List.of(user, noise));

		String result = userLoginService.confirmLogin(login);

		assertNotNull(result);
	}

	@Test
	void test_whenInvalidUserLogsIn_returnNull() {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel noise = new UserModelBuilder().build();
		userRepository.saveAndFlush(noise);

		String result = userLoginService.confirmLogin(login);

		assertNull(result);
	}
}
