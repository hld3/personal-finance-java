package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("h2")
public class UserProfileServiceIntegrationTest {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void test_retrieveUserProfile() {
		String userId = UUID.randomUUID().toString();
		UserModel userModel = new UserModelBuilder().withUserId(userId).build();
		userRepository.saveAndFlush(userModel);

		UserDTO result = userProfileService.retrieveUserProfile(userId);
		assertNotNull(result);
		assertEquals(result.getUserId(), userModel.getUserId());
		assertEquals(result.getFirstName(), userModel.getFirstName());
		assertEquals(result.getLastName(), userModel.getLastName());
		assertEquals(result.getEmail(), userModel.getEmail());
		assertEquals(result.getPhone(), userModel.getPhone());
		assertEquals(result.getCreationDate(), userModel.getCreationDate());
		assertEquals(result.getDateOfBirth(), userModel.getDateOfBirth());
	}

	@Test
	public void test_retrieveUserProfile_isNull() {
		UserDTO result = userProfileService.retrieveUserProfile(UUID.randomUUID().toString());
		assertNull(result);
	}
}
