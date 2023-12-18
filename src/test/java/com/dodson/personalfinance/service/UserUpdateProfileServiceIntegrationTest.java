package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("mysql")
public class UserUpdateProfileServiceIntegrationTest {

	@Autowired
	private UserUpdateProfileService userUpdateProfileService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void test_whenProfileIsFound_thenProfileIsUpdated() {
		UserModel savedUser = new UserModelBuilder().build();
		userRepository.saveAndFlush(savedUser);

		UserDTO expected = new UserDTOBuilder().withUserId(savedUser.getUserId()).build();
		UserModel result = userUpdateProfileService.updateUserProfile(expected);

		assertNotNull(result);
		assertEquals(result.getFirstName(), expected.getFirstName());
		assertEquals(result.getLastName(), expected.getLastName());
		assertEquals(result.getEmail(), expected.getEmail());
		assertEquals(result.getPhone(), expected.getPhone());
		assertEquals(result.getDateOfBirth(), expected.getDateOfBirth());
	}
}
