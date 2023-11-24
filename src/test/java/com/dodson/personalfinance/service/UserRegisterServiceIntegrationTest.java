package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.PasswordHashing;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("mysql")
public class UserRegisterServiceIntegrationTest {

	@Autowired
	private UserRegisterService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	void test_whenRegisteringUser_userIsSaved() throws Exception {
		long startTime = System.currentTimeMillis();
		UserDTO newUser = new UserDTOBuilder().build();
		long stopTime = System.currentTimeMillis();

		String userId = userService.registerNewUser(newUser);
		UserModel savedUser = userRepository.findUserByUserId(userId);
		String hashedPass = PasswordHashing.hashPassword(newUser.getPassword());

		assertEquals(savedUser.getUserId(), userId);
		assertTrue(savedUser.getCreationDate() >= startTime && savedUser.getCreationDate() <= stopTime);
		assertEquals(savedUser.getFirstName(), newUser.getFirstName());
		assertEquals(savedUser.getLastName(), newUser.getLastName());
		assertEquals(savedUser.getEmail(), newUser.getEmail());
		assertEquals(savedUser.getPhone(), newUser.getPhone());
		assertEquals(savedUser.getDateOfBirth(), newUser.getDateOfBirth());
		assertEquals(savedUser.getPasswordHash(), hashedPass);
	}
}
