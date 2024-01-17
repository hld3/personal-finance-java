package com.dodson.personalfinance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.dto.TransactionDTOBuilder;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.repository.TransactionRepository;

public class TransactionSaveServiceUnitTest {

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionSaveService transactionSaveService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void test_whenAddingValidTransaction_thenSuccessful() {
		TransactionDTO transactionDTO = new TransactionDTOBuilder().build();
		transactionSaveService.addTransaction(transactionDTO);

		verify(transactionRepository).save(any(TransactionModel.class));
	}

	@Test
	void test_whenConvertingDTO_thenModelIsReturned() {
		TransactionDTO t = new TransactionDTOBuilder().build();
		TransactionModel result = transactionSaveService.convertDTOToModel(t);

		assertEquals(t.getUserId(), result.getUserId());
		assertEquals(t.getTransactionId(), result.getTransactionId());
		assertEquals(t.getCategoryId(), result.getCategoryId());
		assertEquals(t.getAmount(), result.getAmount());
		assertEquals(t.getDate(), result.getDate());
		assertEquals(t.getDescription(), result.getDescription());
		assertEquals(t.getCreatedAt(), result.getCreatedAt());
		assertEquals(t.getUpdatedAt(), result.getUpdatedAt());
		assertEquals(t.getType(), result.getType());
//		assertEquals(t.getPaymentMethod(), result.getPaymentMethod());
		assertEquals(t.getStatus(), result.getStatus());
	}
}
