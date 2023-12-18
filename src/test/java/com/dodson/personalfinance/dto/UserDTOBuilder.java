package com.dodson.personalfinance.dto;

import java.util.UUID;

import com.github.javafaker.Faker;

public class UserDTOBuilder {

	Faker faker = new Faker();
	String userId = UUID.randomUUID().toString();
	
	public UserDTO build() {
		UserDTO user = new UserDTO();
		user.setUserId(userId);
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().emailAddress());
		user.setPhone(faker.phoneNumber().phoneNumber());
		user.setDateOfBirth(faker.random().nextLong());
		user.setCreationDate(faker.random().nextLong());
		user.setPassword(faker.internet().password());

		return user;
	}

	public UserDTOBuilder withUserId(String userId) {
		this.userId = userId;
		return this;
	}
}
