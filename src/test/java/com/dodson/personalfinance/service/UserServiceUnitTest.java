package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.PasswordHashing;

public class UserServiceUnitTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test 
	void test_registerNewUser_saveUserToRepository() {
		UserDTO expectedUser = new UserDTOBuilder().build();
		userService.registerNewUser(expectedUser);

		verify(userRepository).save(any(UserModel.class));
	}

	@Test
	void test_mapNewUser_returnsExpectedUserModel() throws Exception {
		UserDTO userDTO = new UserDTOBuilder().build();
		UserModel result = userService.mapNewUser(userDTO);

		String hashedPass = PasswordHashing.hashPassword(userDTO.getPassword());

		assertNotNull(result.getUserId()); // generated by the service.
		assertNotNull(result.getCreationDate()); // generated by the service.
		assertEquals(result.getFirstName(), userDTO.getFirstName());
		assertEquals(result.getLastName(), userDTO.getLastName());
		assertEquals(result.getEmail(), userDTO.getEmail());
		assertEquals(result.getPhone(), userDTO.getPhone());
		assertEquals(result.getDateOfBirth(), userDTO.getDateOfBirth());
		assertEquals(result.getPasswordHash(), hashedPass);
	}
}
