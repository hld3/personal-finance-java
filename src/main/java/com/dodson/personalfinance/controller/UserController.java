package com.dodson.personalfinance.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.service.UserLoginService;
import com.dodson.personalfinance.service.UserRegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserRegisterService registerService;
	private UserLoginService loginService;

	UserController(UserRegisterService userService) {
		this.registerService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserDTO user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = constructErrors(bindingResult);
			logger.error("There was an error with the register request: " + errors);
			return ResponseEntity.badRequest().body(Map.of("errors", errors));
		}

		try {
			registerService.registerNewUser(user);
			return ResponseEntity.ok("User registered successfully.");
		} catch (Exception e) {
			logger.error("Error registering new user: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error registering user: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@Valid @RequestBody LoginDTO login, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = constructErrors(bindingResult);
			logger.error("There was an error with the login request: " + errors);
			return ResponseEntity.badRequest().body(Map.of("errors", errors));
		}

		try {
			String token = loginService.confirmLogin(login);
			if (token != null) {
				return ResponseEntity.ok(token);
			} else {
				logger.error("Login for user: " + login.getEmail() + " failed.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error checking user login credentials: " + e.getMessage());
		}
	}

	private Map<String, String> constructErrors(BindingResult bindingResult) {
		return bindingResult.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
	}
}
