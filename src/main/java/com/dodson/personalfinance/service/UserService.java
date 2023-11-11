package com.dodson.personalfinance.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	String registerNewUser(UserDTO user) {
		UserModel newUser = mapNewUser(user);
		// TODO Validate the user DTO
		// TODO hash password
		
		// Save the user to the database
		userRepository.save(newUser);
		return newUser.getUserId();
	}

	UserModel mapNewUser(UserDTO fromUser) {
		UserModel toUser = new UserModel();
		toUser.setUserId(UUID.randomUUID().toString());
		toUser.setFirstName(fromUser.getFirstName());
		toUser.setLastName(fromUser.getLastName());
		toUser.setEmail(fromUser.getEmail());
		toUser.setPhone(fromUser.getPhone());
		toUser.setDateOfBirth(fromUser.getDateOfBirth());
		toUser.setCreationDate(LocalDate.now());
		toUser.setPasswordHash(fromUser.getPasswordHash());
		return toUser;
	}
}
