package com.dodson.personalfinance.dto;

import com.dodson.personalfinance.model.TransactionMethod;
import com.dodson.personalfinance.model.TransactionStatus;
import com.dodson.personalfinance.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class TransactionDTO {

	@NotNull
	@JsonProperty
	private String userId;

	@NotNull
	@JsonProperty
	private String transactionId;

	@JsonProperty
	private long categoryId;

	@NotNull
	@JsonProperty
	private double amount;

	@JsonProperty
	private long date;

	@JsonProperty
	private String description;

	@JsonProperty
	private long createdAt;

	@JsonProperty
	private long updatedAt;

	@JsonProperty
	private TransactionType type;

	@JsonProperty
	private TransactionMethod paymentMethod;

	@JsonProperty
	private TransactionStatus status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public TransactionMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
}
