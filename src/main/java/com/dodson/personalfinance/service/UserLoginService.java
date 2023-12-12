package com.dodson.personalfinance.service;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.LoginDTO;
import com.dodson.personalfinance.dto.UserDTO;
import com.dodson.personalfinance.dto.UserProfileDTO;
import com.dodson.personalfinance.model.UserModel;
import com.dodson.personalfinance.repository.UserRepository;
import com.dodson.personalfinance.utility.JWTToken;
import com.dodson.personalfinance.utility.PasswordHashing;

@Service
public class UserLoginService {

	private Logger logger = LoggerFactory.getLogger(UserLoginService.class);
	private UserRepository userRepository;

	UserLoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserProfileDTO confirmLogin(LoginDTO login) {
		UserModel user = userRepository.retrievePasswordHashByEmail(login.getEmail());

		if (user != null) {
			try {
				UserProfileDTO profile = populateProfileDTO(user);
				String loginHashedPW = PasswordHashing.hashPassword(login.getPassword());
				if (loginHashedPW.equals(user.getPasswordHash())) {
					String token = JWTToken.createJWTToken(user.getUserId());
					profile.setJWTToken(token);
					return profile;
				}
			} catch (NoSuchAlgorithmException e) {
				logger.error("There was an error getting password hash to login, " + e.getMessage());
			}
		}
		return null;
	}

	private UserProfileDTO populateProfileDTO(UserModel userModel) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userModel.getUserId());
		userDTO.setFirstName(userModel.getFirstName());
		userDTO.setLastName(userModel.getLastName());
		userDTO.setEmail(userModel.getEmail());
		userDTO.setPhone(userModel.getPhone());
		userDTO.setDateOfBirth(userModel.getDateOfBirth());
		userDTO.setCreationDate(userModel.getCreationDate());

		UserProfileDTO userProfileDTO = new UserProfileDTO();
		userProfileDTO.setUserDTO(userDTO);
		return userProfileDTO;
	}
}
