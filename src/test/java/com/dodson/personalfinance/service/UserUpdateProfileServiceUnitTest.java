package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;

public class UserUpdateProfileServiceUnitTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserUpdateProfileService userUpdateProfileService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void test_whenProfileIsFound_thenProfileIsUpdated() {
		String userId = UUID.randomUUID().toString();
		UserDTO expected = new UserDTOBuilder().withUserId(userId).build();
		UserModel savedUser = new UserModelBuilder().withUserId(userId).withProfileData(expected.getFirstName(), expected.getLastName(), expected.getEmail(), expected.getPhone(), expected.getDateOfBirth()).build();
		when(userRepository.findUserByUserId(any(String.class))).thenReturn(new UserModelBuilder().build());
		when(userRepository.save(any(UserModel.class))).thenReturn(savedUser);

		UserModel result = userUpdateProfileService.updateUserProfile(expected);

		assertNotNull(result);
		assertEquals(result.getFirstName(), expected.getFirstName());
		assertEquals(result.getLastName(), expected.getLastName());
		assertEquals(result.getEmail(), expected.getEmail());
		assertEquals(result.getPhone(), expected.getPhone());
		assertEquals(result.getDateOfBirth(), expected.getDateOfBirth());
	}
}
