package com.dodson.personalfinance.model;

import java.util.UUID;

import com.github.javafaker.Faker;

public class UserModelBuilder {

	Faker faker = new Faker();
	private String userId = UUID.randomUUID().toString();
	private String passwordHash = faker.crypto().sha256();
	private String email = faker.internet().emailAddress();

	public UserModel build() {
		UserModel user = new UserModel();
		user.setUserId(userId);
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(email);
		user.setPhone(faker.phoneNumber().phoneNumber());
		user.setDateOfBirth(faker.random().nextLong());
		user.setCreationDate(faker.random().nextLong());
		user.setPasswordHash(passwordHash);

		return user;
	}

	public UserModelBuilder withPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}

	public UserModelBuilder withUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public UserModelBuilder withEmailAndPassword(String email, String passwordHash) {
		this.email = email;
		this.passwordHash = passwordHash;
		return this;
	}
}
