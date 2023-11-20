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

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserDTO user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = bindingResult.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

			logger.error("There was an error with the request: " + errors);
			return ResponseEntity.badRequest().body(Map.of("errors", errors));
		}

		try {
			userService.registerNewUser(user);
			return ResponseEntity.ok("User registered successfully.");
		} catch (Exception e) {
			logger.error("Error registering new user: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
		}
	}
}

