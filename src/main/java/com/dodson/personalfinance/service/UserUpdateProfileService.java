package com.dodson.personalfinance.service;

import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;

@Service
public class UserUpdateProfileService {

	private UserRepository userRepository;

	public UserUpdateProfileService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserModel updateUserProfile(UserDTO userDTO) {
		UserModel userModel = userRepository.findUserByUserId(userDTO.getUserId());
		updateModel(userModel, userDTO);
		return userRepository.save(userModel);
	}

	private void updateModel(UserModel toUser, UserDTO fromUser) {
		toUser.setFirstName(fromUser.getFirstName());
		toUser.setLastName(fromUser.getLastName());
		toUser.setEmail(fromUser.getEmail());
		toUser.setPhone(fromUser.getPhone());
		toUser.setDateOfBirth(fromUser.getDateOfBirth());
	}
}
