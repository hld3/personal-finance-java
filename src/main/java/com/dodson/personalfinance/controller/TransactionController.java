package com.dodson.personalfinance.controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.repository.TransactionRepository;
import com.dodson.personalfinance.service.TransactionSaveService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	private TransactionSaveService transactionSaveService;
	private TransactionRepository transactionRepository;

	TransactionController(TransactionSaveService transactionSaveService, TransactionRepository transactionRepository) {
		this.transactionSaveService = transactionSaveService;
		this.transactionRepository = transactionRepository;
	}

	// just messing with htmx.
	@GetMapping
	String getTransactions(Model model) {
		// TODO figure out the enum constant error. There is something wrong with TransactionMethod enum. It can't find it for some reason.

		model.addAttribute("transactions", transactionRepository.findAll());
		return "transactions";
	}

	@PostMapping("/save")
	public ResponseEntity<?> addNewTransaction(@Valid @RequestBody TransactionDTO transactionDTO,
			BindingResult bindingResult) {
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
