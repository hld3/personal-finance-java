package com.dodson.personalfinance.utility;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTToken {

	private static Logger logger = LoggerFactory.getLogger(JWTToken.class);

	public static String createJWTToken(String userId) {
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret"); // TODO I should do something about the secret key.

			String token = JWT.create()
					.withIssuer("auth0") // what should this be?
					.withSubject(userId.toString())
					.withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
					.sign(algorithm);

			logger.info("Created JWT token for user: " + userId);
			return token;
		} catch (Exception e) {
			logger.error("There was an error creating JWT token: " + e.getMessage());
			return null;
		}
	}
}
