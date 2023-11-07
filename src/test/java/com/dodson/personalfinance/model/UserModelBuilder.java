package com.dodson.personalfinance.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import com.github.javafaker.Faker;

public class UserModelBuilder {

	Faker faker = new Faker();
	private UUID userId = UUID.randomUUID();

	public UserModel build() {
		UserModel user = new UserModel();
		user.setUserId(userId);
		user.setFirstName(faker.name().firstName());
		user.setLastName(faker.name().lastName());
		user.setEmail(faker.internet().emailAddress());
		user.setPhone(faker.phoneNumber().phoneNumber());
		user.setDateOfBirth(createRandomDate());
		user.setCreationDate(createRandomDate());
		user.setPasswordHash(faker.internet().password());

		return user;
	}

	public LocalDate createRandomDate() {
		Date fakeDate = faker.date().between(
				Date.from(LocalDate.of(1999, 12, 19).atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(LocalDate.of(2023, 12, 19).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		return fakeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public UserModelBuilder withUserId(UUID userId) {
		this.userId = userId;
		return this;
	}

}
