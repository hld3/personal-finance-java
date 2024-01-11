package com.dodson.personalfinance.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.service.TransactionSaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	private TransactionSaveService transactionSaveService;

	TransactionController(TransactionSaveService transactionSaveService) {
		this.transactionSaveService = transactionSaveService;
	}

	@PostMapping("/save")
	public ResponseEntity<?> addNewTransaction(@Valid @RequestBody TransactionDTO transactionDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = constructErrors(bindingResult);
			logger.error("There was an error with the register request: " + errors);
			return ResponseEntity.badRequest().body(Map.of("errors", errors));
		}

		TransactionModel tm = transactionSaveService.addTransaction(transactionDTO);
		if (tm != null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	private Map<String, String> constructErrors(BindingResult bindingResult) {
		return bindingResult.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
	}
}
