package com.dodson.personalfinance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.model.TransactionMethod;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.model.TransactionStatus;
import com.dodson.personalfinance.model.TransactionType;
import com.dodson.personalfinance.repository.TransactionRepository;
import com.dodson.personalfinance.service.TransactionSaveService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

	private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	private TransactionSaveService transactionSaveService;
	private TransactionRepository transactionRepository;

	TransactionController(TransactionSaveService transactionSaveService,
			TransactionRepository transactionRepository) {
		this.transactionSaveService = transactionSaveService;
		this.transactionRepository = transactionRepository;
	}

	// just messing with htmx.
	@GetMapping(value = "/{userId}")
	String getTransactions(Model model, @PathVariable String userId) {
		model.addAttribute("transactions", transactionRepository.findAllByUserId(userId));
		return "transactions";
	}

	@GetMapping("/save")
	public String getSaveForm(Model model) {
		return "newtransaction";
	}

	@PostMapping("/savetest")
	public String addNewTransactionTest(Model model, @RequestParam("description") String description,
			@RequestParam("amount") double amount, @RequestParam("date") String date,
			@RequestParam("type") TransactionType type, @RequestParam("status") TransactionStatus status,
			@RequestParam("method") TransactionMethod method) {

		TransactionModel tm = new TransactionModel();
		tm.setUserId("3becfd46-c42b-4243-9f9e-c207bf9115f6"); // test user.
		tm.setTransactionId(UUID.randomUUID().toString());
		tm.setDescription(description);
		tm.setAmount(amount);
		tm.setDate(convertDateToMillis(date));
		tm.setType(type);
		tm.setStatus(status);
		tm.setPaymentMethod(method);
		tm.setCreatedAt(System.currentTimeMillis());

		// this.transactionRepository.save(tm);

		model.addAttribute("transaction", tm);
		return "appendTransaction";
	}

	private long convertDateToMillis(String date) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
		LocalDateTime localDateTime = localDate.atStartOfDay(); // Start of the day
		return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
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
