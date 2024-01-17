package com.dodson.personalfinance.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.dto.TransactionDTOBuilder;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper om;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void test_whenAddingValidTransaction_thenTransactionIsSaved() throws Exception {
		TransactionDTO transactionDTO = new TransactionDTOBuilder().build();

		//TODO retrieve transaction and compare fields.

		mockMvc.perform(post("/transaction/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(transactionDTO)))
				.andExpect(status().isOk());

		TransactionModel result = transactionRepository.findByTransactionId(transactionDTO.getTransactionId());
		assertNotNull(result);
		assertEquals(result.getUserId(), transactionDTO.getUserId());
		assertEquals(result.getCategoryId(), transactionDTO.getCategoryId());
		assertEquals(result.getAmount(), transactionDTO.getAmount());
		assertEquals(result.getDate(), transactionDTO.getDate());
		assertEquals(result.getDescription(), transactionDTO.getDescription());
		assertEquals(result.getType(), transactionDTO.getType());
//		assertEquals(result.getPaymentMethod(), transactionDTO.getPaymentMethod());
		assertEquals(result.getStatus(), transactionDTO.getStatus());

		// This works for now but the service has a todo that I will likely never update.
		assertEquals(result.getCreatedAt(), transactionDTO.getCreatedAt());
		assertEquals(result.getUpdatedAt(), transactionDTO.getUpdatedAt());
	}

	// TODO test validation?
}
