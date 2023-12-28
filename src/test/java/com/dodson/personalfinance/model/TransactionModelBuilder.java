package com.dodson.personalfinance.model;

import java.util.UUID;

import com.github.javafaker.Faker;

public class TransactionModelBuilder {

	Faker faker = new Faker();
	
	public TransactionModel build() {
		TransactionModel tm = new TransactionModel();
		tm.setUserId(UUID.randomUUID().toString());
		tm.setTransactionId(UUID.randomUUID().toString());
		tm.setCategoryId(faker.number().randomNumber());
		tm.setAmount((int) faker.number().randomNumber(3, true));
		tm.setDate(faker.number().randomNumber());
		tm.setDescription(faker.lorem().sentence());
		tm.setCreatedAt(faker.number().randomNumber());
		tm.setUpdatedAt(faker.number().randomNumber());
		tm.setType(getRandomEnum(TransactionType.class));
		tm.setPaymentMethod(getRandomEnum(TransactionMethod.class));
		tm.setStatus(getRandomEnum(TransactionStatus.class));

		return tm;
	}

	private <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
		int ord = faker.random().nextInt(enumClass.getEnumConstants().length);
		return enumClass.getEnumConstants()[ord];
	}
}
