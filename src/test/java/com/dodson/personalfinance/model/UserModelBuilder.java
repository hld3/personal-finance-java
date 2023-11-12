package com.dodson.personalfinance.model;

import java.util.UUID;

import com.github.javafaker.Faker;

public class UserModelBuilder {

	Faker faker = new Faker();
	private String userId = UUID.randomUUID().toString();

	public UserModel build() {
		UserModel user = new UserModel();
		user.setUserId(userId);
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().emailAddress());
		user.setPhone(faker.phoneNumber().phoneNumber());
		user.setDateOfBirth(faker.random().nextLong());
		user.setCreationDate(faker.random().nextLong());
		user.setPasswordHash(faker.internet().password());

		return user;
	}

	public UserModelBuilder withUserId(String userId) {
		this.userId = userId;
		return this;
	}
}
