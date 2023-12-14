package com.dodson.personalfinance.dto;

import com.github.javafaker.Faker;

public class UserProfileDTOBuilder {

	Faker faker = new Faker();

	public UserProfileDTO build() {
		UserProfileDTO profile = new UserProfileDTO();
		profile.setUserDTO(new UserDTOBuilder().build());
		profile.setJWTToken(faker.crypto().md5());

		return profile;
	}
}
