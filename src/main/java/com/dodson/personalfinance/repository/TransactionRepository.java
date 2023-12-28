package com.dodson.personalfinance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dodson.personalfinance.model.TransactionModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

	TransactionModel findByTransactionId(String transactionId);
}
