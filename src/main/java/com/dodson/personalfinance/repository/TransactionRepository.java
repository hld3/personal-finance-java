package com.dodson.personalfinance.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dodson.personalfinance.model.TransactionModel;

public interface TransactionRepository extends CrudRepository<TransactionModel, Long> {

	TransactionModel findByTransactionId(String transactionId);

	@Query("Select tm from transaction_model tm where userId = :userId")
	List<TransactionModel> findAllByUserId(@Param("userId") String userId);
}
