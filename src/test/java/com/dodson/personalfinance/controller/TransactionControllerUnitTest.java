package com.dodson.personalfinance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.model.TransactionModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.dto.TransactionDTOBuilder;
import com.dodson.personalfinance.service.TransactionSaveService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerUnitTest {

	private MockMvc mockMvc;
	private ObjectMapper om = new ObjectMapper();

	@Mock
	private TransactionSaveService transactionSaveService;

	@InjectMocks
	private TransactionController transactionController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
	}

	@Test
	public void test_whenValidTransactionIsSent_thenStatusIsOk() throws Exception {
		TransactionModel transactionModel = new TransactionModelBuilder().build();
		when(transactionSaveService.addTransaction(any(TransactionDTO.class))).thenReturn(transactionModel);

		mockMvc.perform(post("/transaction/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(new TransactionDTOBuilder().build())))
				.andExpect(status().isOk());
	}

	@Test
	public void test_whenNullTransaction_thenStatusIsBad() throws Exception {
		when(transactionSaveService.addTransaction(any(TransactionDTO.class))).thenReturn(null);

		mockMvc.perform(post("/transaction/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(new TransactionDTOBuilder().build())))
				.andExpect(status().isBadRequest());
	}
}
