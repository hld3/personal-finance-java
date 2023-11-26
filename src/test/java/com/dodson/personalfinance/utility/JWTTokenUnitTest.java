package com.dodson.personalfinance.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTTokenUnitTest {

	@Test
	void testCreateJWTToken() {
		String token = JWTToken.createJWTToken(UUID.randomUUID());
		assertNotNull(token);
	}

	@Test
	public void testTokenExpiration() {
		String token = JWTToken.createJWTToken(UUID.randomUUID());
		DecodedJWT decoded = JWT.decode(token);
		assertTrue(decoded.getExpiresAt().after(new Date()),
				"Token Expiration time should be after current time");
	}

	@Test
	public void testTokenSubject() {
		UUID userId = UUID.randomUUID();
		String token = JWTToken.createJWTToken(userId);
		DecodedJWT decoded = JWT.decode(token);
		assertEquals(userId.toString(), decoded.getSubject(), "Subject UUIDs should match");
	}

	@Test
	public void testTokenIssuer() {
		String token = JWTToken.createJWTToken(UUID.randomUUID());
		DecodedJWT decoded = JWT.decode(token);
		assertEquals("auth0", decoded.getIssuer(), "Issuer should be 'auth0'");
	}
}

