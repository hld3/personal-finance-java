package com.dodson.personalfinance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

	@JsonProperty
	private String userId;

	@JsonProperty
	private String firstName;

	@JsonProperty
	private String lastName;

	@Email
	@JsonProperty
	private String email;

	@JsonProperty
	private String phone;

	@JsonProperty
	private long dateOfBirth;

	@JsonProperty
	private long creationDate;

	@NotNull
	@JsonProperty
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passwordHash) {
		this.password = passwordHash;
	}
}
