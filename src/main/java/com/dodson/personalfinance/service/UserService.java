package com.dodson.personalfinance.service;

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

	public String registerNewUser(UserDTO user) {
		UserModel newUser = mapNewUser(user);
		
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
		toUser.setCreationDate(System.currentTimeMillis());
		toUser.setPasswordHash(fromUser.getPasswordHash()); // TODO hash password
		return toUser;
	}
}
