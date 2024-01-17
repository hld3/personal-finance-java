package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.dto.TransactionDTOBuilder;
import com.dodson.personalfinance.model.TransactionModel;

@SpringBootTest
public class TransactionSaveServiceIntegrationTest {

	@Autowired
	private TransactionSaveService transactionSaveService;

	@Test
	void test_whenAddingValidTransaction_thenSuccessful() {
		TransactionDTO transactionDTO = new TransactionDTOBuilder().build();

		TransactionModel result = transactionSaveService.addTransaction(transactionDTO);

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
//		assertEquals(result.getPaymentMethod(), transactionDTO.getPaymentMethod());
		assertEquals(result.getStatus(), transactionDTO.getStatus());
	}

	@Test
	void test_whenTransactionIsNull_thenResultIsNull() {
		TransactionModel result = transactionSaveService.addTransaction(null);
		assertNull(result);
	}
}
