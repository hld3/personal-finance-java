package com.dodson.personalfinance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.LoginDTOBuilder;
import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.dto.UserProfileDTO;
import com.dodson.personalfinance.dto.UserProfileDTOBuilder;
import com.dodson.personalfinance.service.UserLoginService;
import com.dodson.personalfinance.service.UserRegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

	private MockMvc mockMvc;

	@Mock
	private UserRegisterService registerService;

	@Mock
	private UserLoginService loginService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void test_registerNewUser() throws Exception {
		UserDTO user = new UserDTOBuilder().build();

		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void test_registerInvalidUserDTOData() throws Exception {
		UserDTO user = new UserDTOBuilder().build();
		user.setEmail("not an email");

		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").exists())
				.andExpect(jsonPath("$.errors.email").value("must be a well-formed email address"));
	}

	@Test
	public void test_whenThrowsException_thenResponseStatusIsInternalServerError() throws Exception {
		UserDTO user = new UserDTOBuilder().build();
		doThrow(new RuntimeException("Internal error")).when(registerService)
				.registerNewUser(any(UserDTO.class));

		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user)))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void test_whenLoginIsSuccessful_returnsProfileDataAndToken() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		UserProfileDTO expected = new UserProfileDTOBuilder().build();
		when(loginService.confirmLogin(any(LoginDTO.class))).thenReturn(expected);

		mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(login)))
				.andExpect(status().isOk());
	}

	@Test
	public void test_whenLoginFails_thenUnauthorized() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		when(loginService.confirmLogin(any(LoginDTO.class))).thenReturn(null);

		mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(login)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void test_whenLoginMethodFails_thenInternalServerError() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		doThrow(new RuntimeException("Internal error")).when(loginService)
				.confirmLogin(any(LoginDTO.class));

		mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(login)))
				.andExpect(status().isInternalServerError());
	}
}
