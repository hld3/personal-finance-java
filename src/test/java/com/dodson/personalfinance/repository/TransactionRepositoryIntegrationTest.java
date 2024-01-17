package com.dodson.personalfinance.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
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

		TransactionModel savedTm = transactionRepository.save(tm);

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
		transactionRepository.save(tm);

		TransactionModel savedTm = transactionRepository.findByTransactionId(tm.getTransactionId());

		assertThat(tm, is(savedTm));
	}

	@Test
	public void test_findAllByUserId() {
		List<TransactionModel> expectedTransactions = new ArrayList<>();
		String expectedUserId = UUID.randomUUID().toString();
		expectedTransactions.add(new TransactionModelBuilder().withUserId(expectedUserId).build());
		expectedTransactions.add(new TransactionModelBuilder().withUserId(expectedUserId).build());
		expectedTransactions.add(new TransactionModelBuilder().withUserId(expectedUserId).build());
		transactionRepository.saveAll(expectedTransactions);

		//noise
		List<TransactionModel> noise = new ArrayList<>();
		noise.add(new TransactionModelBuilder().build());
		noise.add(new TransactionModelBuilder().build());
		noise.add(new TransactionModelBuilder().build());
		transactionRepository.saveAll(noise);

		List<TransactionModel> results = transactionRepository.findAllByUserId(expectedUserId);
		
		assertThat(results.size(), is(expectedTransactions.size()));
		results.forEach(p -> assertThat(p.getUserId(), is(expectedUserId)));
	}
}
