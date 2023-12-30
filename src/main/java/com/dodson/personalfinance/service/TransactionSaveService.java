package com.dodson.personalfinance.service;

import org.springframework.stereotype.Service;

import com.dodson.personalfinance.dto.TransactionDTO;
import com.dodson.personalfinance.model.TransactionModel;
import com.dodson.personalfinance.repository.TransactionRepository;

@Service
public class TransactionSaveService {

	private TransactionRepository transactionRepository;

	TransactionSaveService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void addTransaction(TransactionDTO transactionDTO) {
		TransactionModel tm = convertDTOToModel(transactionDTO);
		transactionRepository.save(tm);
	}

	TransactionModel convertDTOToModel(TransactionDTO transactionDTO) {
		TransactionModel tm = new TransactionModel();
		tm.setUserId(transactionDTO.getUserId());
		tm.setTransactionId(transactionDTO.getTransactionId());
		tm.setCategoryId(transactionDTO.getCategoryId());
		tm.setAmount(transactionDTO.getAmount());
		tm.setDate(transactionDTO.getDate());
		tm.setDescription(transactionDTO.getDescription());
		tm.setCreatedAt(transactionDTO.getCreatedAt());
		tm.setUpdatedAt(transactionDTO.getUpdatedAt());
		tm.setType(transactionDTO.getType());
		tm.setPaymentMethod(transactionDTO.getPaymentMethod());
		tm.setStatus(transactionDTO.getStatus());

		return tm;
	}
}
