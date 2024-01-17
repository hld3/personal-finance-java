package com.dodson.personalfinance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.repository.TransactionRepository;

@Service
public class TransactionSaveService {

	private Logger logger = LoggerFactory.getLogger(TransactionSaveService.class);
	private TransactionRepository transactionRepository;

	TransactionSaveService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public TransactionModel addTransaction(TransactionDTO transactionDTO) {
		if (transactionDTO != null) {// TODO I don't think null would be able to get through validation. Check after the controller tests are done.
			TransactionModel tm = convertDTOToModel(transactionDTO);
			try {
				return transactionRepository.save(tm);
			} catch (IllegalArgumentException e) {
				logger.error("Error saving transaction: " + e.getMessage());
			}
		}
		return null;
	}

	TransactionModel convertDTOToModel(TransactionDTO transactionDTO) {
		TransactionModel tm = new TransactionModel();
		tm.setUserId(transactionDTO.getUserId());
		tm.setTransactionId(transactionDTO.getTransactionId());
		tm.setCategoryId(transactionDTO.getCategoryId());
		tm.setAmount(transactionDTO.getAmount());
		tm.setDate(transactionDTO.getDate());
		tm.setDescription(transactionDTO.getDescription());
		tm.setType(transactionDTO.getType());
		// tm.setPaymentMethod(transactionDTO.getPaymentMethod());
		tm.setStatus(transactionDTO.getStatus());
		// TODO these should probably be set to the current time.
		tm.setCreatedAt(transactionDTO.getCreatedAt());
		tm.setUpdatedAt(transactionDTO.getUpdatedAt());

		return tm;
	}
}
