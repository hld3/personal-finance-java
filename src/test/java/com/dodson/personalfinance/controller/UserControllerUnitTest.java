package com.dodson.personalfinance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void test_registerNewUser() throws Exception {
		UserDTO user = new UserDTOBuilder().build();
		System.out.println(new ObjectMapper().writeValueAsString(user));

		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isOk());
	}

	@Test
	public void test_registerInvalidUserDTOData() throws Exception {
		UserDTO user = new UserDTOBuilder().build();
		user.setUserId(null);
		user.setEmail("not an email");

		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isInternalServerError());
	}
}
