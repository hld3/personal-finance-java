package com.dodson.personalfinance.dto;

import com.github.javafaker.Faker;

public class LoginDTOBuilder {

	Faker faker = new Faker();

	public LoginDTO build() {
		LoginDTO login = new LoginDTO();
		login.setEmail(faker.internet().emailAddress());
		login.setPassword(faker.internet().password());

		return login;
	}
}
