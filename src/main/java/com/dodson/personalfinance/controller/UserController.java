package com.dodson.personalfinance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserDTO user) {
		try {
			userService.registerNewUser(user);
			return ResponseEntity.ok("User registered successfully");
		} catch (Exception e) {
			// TODO logging.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
		}
	}
}

