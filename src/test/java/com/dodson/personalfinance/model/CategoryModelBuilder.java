package com.dodson.personalfinance.model;

import java.util.UUID;

import com.github.javafaker.Faker;

public class CategoryModelBuilder {

	Faker faker = new Faker();

	public CategoryModel build() {
		CategoryModel cm = new CategoryModel();
		cm.setUserId(UUID.randomUUID().toString());
		cm.setName(faker.code().isbn10());
		cm.setDescription(faker.lorem().sentence());

		return cm;
	}
}
