package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.dto.TransactionDTOBuilder;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.repository.TransactionRepository;

@SpringBootTest
public class TransactionSaveServiceIntegrationTest {

	@Autowired
	private TransactionSaveService transactionSaveService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	void test_whenAddingValidTransaction_thenSuccessful() {
		TransactionDTO transactionDTO = new TransactionDTOBuilder().build();
		transactionSaveService.addTransaction(transactionDTO);

		TransactionModel result = transactionRepository.findByTransactionId(transactionDTO.getTransactionId());

		assertNotNull(result);
		assertEquals(result.getUserId(), transactionDTO.getUserId());
		assertEquals(result.getTransactionId(), transactionDTO.getTransactionId());
		assertEquals(result.getCategoryId(), transactionDTO.getCategoryId());
		assertEquals(result.getAmount(), transactionDTO.getAmount());
		assertEquals(result.getDate(), transactionDTO.getDate());
		assertEquals(result.getDescription(), transactionDTO.getDescription());
		assertEquals(result.getCreatedAt(), transactionDTO.getCreatedAt());
		assertEquals(result.getUpdatedAt(), transactionDTO.getUpdatedAt());
		assertEquals(result.getType(), transactionDTO.getType());
		assertEquals(result.getPaymentMethod(), transactionDTO.getPaymentMethod());
		assertEquals(result.getStatus(), transactionDTO.getStatus());
	}
}
