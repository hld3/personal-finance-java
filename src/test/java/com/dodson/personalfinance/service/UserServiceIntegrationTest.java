package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UserServiceIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	void test_whenRegisteringUser_userIsSaved() {
		long startTime = System.currentTimeMillis();
		UserDTO newUser = new UserDTOBuilder().build();
		long stopTime = System.currentTimeMillis();

		String userId = userService.registerNewUser(newUser);
		UserModel savedUser = userRepository.findUserByUserId(userId);

		assertEquals(savedUser.getUserId(), userId);
		assertTrue(savedUser.getCreationDate() >= startTime && savedUser.getCreationDate() <= stopTime);
		// assertEquals(savedUser.getCreationDate(), System.currentTimeMillis());
		assertEquals(savedUser.getFirstName(), newUser.getFirstName());
		assertEquals(savedUser.getLastName(), newUser.getLastName());
		assertEquals(savedUser.getEmail(), newUser.getEmail());
		assertEquals(savedUser.getPhone(), newUser.getPhone());
		assertEquals(savedUser.getDateOfBirth(), newUser.getDateOfBirth());
		assertEquals(savedUser.getPasswordHash(), newUser.getPasswordHash());
	}
}
