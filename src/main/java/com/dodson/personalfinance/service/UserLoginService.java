package com.dodson.personalfinance.service;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.LoginDTO;
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

	public String confirmLogin(LoginDTO login) {
		UserModel user = userRepository.retrievePasswordHashByEmail(login.getEmail());

		try {
			String loginHashedPW = PasswordHashing.hashPassword(login.getPassword());
			if (loginHashedPW.equals(user.getPasswordHash())) {
				String token = JWTToken.createJWTToken(user.getUserId());
				return token;
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("There was an error getting password hash to login, " + e.getMessage());
		}
		return null;
	}
}
