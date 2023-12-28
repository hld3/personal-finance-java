package com.dodson.personalfinance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_model")
public class TransactionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_id", columnDefinition = "char(36)", nullable = false)
	private String userId;

	@Column(name = "transaction_id", columnDefinition = "char(36)", nullable = false)
	private String transactionId;

	@PrimaryKeyJoinColumn
	@Column(name = "category_id")
	private long categoryId;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "date", nullable = false)
	private long date;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "created_at", nullable = false)
	private long createdAt;
	
	@Column(name = "updated_at")
	private long updatedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 10)
	private TransactionType type;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", length = 20)
	private TransactionMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 20)
	private TransactionStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
