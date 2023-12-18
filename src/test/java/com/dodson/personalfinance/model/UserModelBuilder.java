package com.dodson.personalfinance.model;

import java.util.UUID;

import com.github.javafaker.Faker;

public class UserModelBuilder {

	Faker faker = new Faker();
	private String userId = UUID.randomUUID().toString();
	private String passwordHash = faker.crypto().sha256();
	private String email = faker.internet().emailAddress();
	private String firstName = faker.name().firstName();
	private String lastName = faker.name().firstName();
	private String phone = faker.phoneNumber().phoneNumber();
	private long dateOfBirth = faker.random().nextLong();

	public UserModel build() {
		UserModel user = new UserModel();
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setDateOfBirth(dateOfBirth);
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

	public UserModelBuilder withProfileData(String firstName, String lastName, String email, String phone, long dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		return this;
	}
}
