package com.dodson.personalfinance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;

@Service
public class UserProfileService {

	private Logger logger = LoggerFactory.getLogger(UserProfileService.class);
	private UserRepository userRepository;

	UserProfileService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDTO retrieveUserProfile(String userId) {
		UserModel userModel = userRepository.findUserByUserId(userId);
		if (userModel == null) {
			logger.info("User not found: " + userId);
			return null;
		}
		return convertModelToDTO(userModel);
	}

	private UserDTO convertModelToDTO(UserModel um) {
		UserDTO dto = new UserDTO();
		dto.setUserId(um.getUserId());
		dto.setFirstName(um.getFirstName());
		dto.setLastName(um.getLastName());
		dto.setEmail(um.getEmail());
		dto.setPhone(um.getPhone());
		dto.setCreationDate(um.getCreationDate());
		dto.setDateOfBirth(um.getDateOfBirth());

		return dto;
	}
}
