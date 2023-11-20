package com.dodson.personalfinance.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {

	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		byte[] byteData = md.digest();

		StringBuilder hexString = new StringBuilder();
		for (byte b : byteData) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('O');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
