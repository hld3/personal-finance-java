package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;

public class UserProfileServiceUnitTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserProfileService userProfileService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void test_retrieveUserProfile() {
		String userId = UUID.randomUUID().toString();
		UserModel userModel = new UserModelBuilder().withUserId(userId).build();
		when(userRepository.findUserByUserId(userId)).thenReturn(userModel);

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
		String userId = UUID.randomUUID().toString();
		when(userRepository.findUserByUserId(userId)).thenReturn(null);

		UserDTO result = userProfileService.retrieveUserProfile(userId);
		assertNull(result);
	}
}
