package com.dodson.personalfinance.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.model.TransactionModelBuilder;

@DataJpaTest
@ActiveProfiles("h2")
public class TransactionRepositoryIntegrationTest {

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void test_saveTransaction() {
		TransactionModel tm = new TransactionModelBuilder().build();

		TransactionModel savedTm = transactionRepository.saveAndFlush(tm);

		assertThat(tm, is(savedTm));
	}

	@Test
	public void test_getTransactionIsNull_whenNotFound() {
		TransactionModel savedTm = transactionRepository.findByTransactionId(UUID.randomUUID().toString());
		assertNull(savedTm);
	}

	@Test
	public void test_getTransaction() {
		TransactionModel tm = new TransactionModelBuilder().build();
		transactionRepository.saveAndFlush(tm);

		TransactionModel savedTm = transactionRepository.findByTransactionId(tm.getTransactionId());

		assertThat(tm, is(savedTm));
	}
}
