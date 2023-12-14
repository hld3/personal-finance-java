package com.dodson.personalfinance.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.LoginDTOBuilder;
import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserDTOBuilder;
import com.dodson.personalfinance.dto.UserProfileDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.model.UserModelBuilder;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.PasswordHashing;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test_registerNewUser() throws Exception {
		UserDTO user = new UserDTOBuilder().build();

		mockMvc.perform(post("/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(user)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("User registered successfully.")));
	}

	@Test
	public void test_userLogin() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		String pwHash = PasswordHashing.hashPassword(login.getPassword());
		UserModel um = new UserModelBuilder().withEmailAndPassword(login.getEmail(), pwHash).build();
		userRepository.save(um);

		MvcResult result = mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(login)))
				.andExpect(status().isOk())
				.andReturn();

		UserProfileDTO profile = om.readValue(result.getResponse().getContentAsString(), UserProfileDTO.class);
		UserDTO user = profile.getUserDTO();
		assertNotNull(profile.getJWTToken());
		assertEquals(user.getUserId(), um.getUserId());
		assertEquals(user.getFirstName(), um.getFirstName());
		assertEquals(user.getLastName(), um.getLastName());
		assertEquals(user.getEmail(), um.getEmail());
		assertEquals(user.getPhone(), um.getPhone());
		assertEquals(user.getDateOfBirth(), um.getDateOfBirth());
		assertEquals(user.getCreationDate(), um.getCreationDate());
	}

	@Test
	public void test_userLoginFails_unauthorized() throws Exception {
		LoginDTO login = new LoginDTOBuilder().build();
		UserModel um = new UserModelBuilder().withEmailAndPassword(login.getEmail(), "wrong_password").build();
		userRepository.save(um);

		mockMvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(login)))
				.andExpect(status().isUnauthorized());
	}
}
