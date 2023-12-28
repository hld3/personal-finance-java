package com.dodson.personalfinance.dto;

import java.util.UUID;

import com.dodson.personalfinance.model.TransactionMethod;
import com.dodson.personalfinance.model.TransactionStatus;
import com.dodson.personalfinance.model.TransactionType;
import com.github.javafaker.Faker;

public class TransactionDTOBuilder {

	Faker faker = new Faker();

	public TransactionDTO build() {
		TransactionDTO transaction = new TransactionDTO();
		transaction.setUserId(UUID.randomUUID().toString());
		transaction.setTransactionId(UUID.randomUUID().toString());
		transaction.setCategoryId(faker.number().randomNumber());
		transaction.setAmount((int) faker.number().randomNumber(3, true));
		transaction.setDate(faker.number().randomNumber());
		transaction.setDescription(faker.lorem().sentence());
		transaction.setCreatedAt(faker.number().randomNumber());
		transaction.setUpdatedAt(faker.number().randomNumber());
		transaction.setType(getRandomEnum(TransactionType.class));
		transaction.setPaymentMethod(getRandomEnum(TransactionMethod.class));
		transaction.setStatus(getRandomEnum(TransactionStatus.class));

		return transaction;
	}

	private <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
		int ord = faker.random().nextInt(enumClass.getEnumConstants().length);
		return enumClass.getEnumConstants()[ord];
	}
}
