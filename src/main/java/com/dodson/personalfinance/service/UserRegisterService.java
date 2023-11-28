package com.dodson.personalfinance.service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.PasswordHashing;

@Service
public class UserRegisterService {

	private Logger logger = LoggerFactory.getLogger(UserRegisterService.class);

	private UserRepository userRepository;

	UserRegisterService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String registerNewUser(UserDTO user) {
		UserModel newUser = mapNewUser(user);
		
		// Save the user to the database
		userRepository.save(newUser);
		return newUser.getUserId();
	}

	UserModel mapNewUser(UserDTO fromUser) {
		String hashedPass;
		try {
			hashedPass = PasswordHashing.hashPassword(fromUser.getPassword());
		} catch (NoSuchAlgorithmException e) {
			logger.error("There was an error hashing a password, saving as plain text: " + fromUser.getPassword() + ". Error: " + e.getMessage());
			hashedPass = fromUser.getPassword();
		}
		UserModel toUser = new UserModel();
		toUser.setUserId(UUID.randomUUID().toString());
		toUser.setFirstName(fromUser.getFirstName());
		toUser.setLastName(fromUser.getLastName());
		toUser.setEmail(fromUser.getEmail());
		toUser.setPhone(fromUser.getPhone());
		toUser.setDateOfBirth(fromUser.getDateOfBirth());
		toUser.setCreationDate(System.currentTimeMillis());
		toUser.setPasswordHash(hashedPass); 
		return toUser;
	}
}
