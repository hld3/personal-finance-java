package com.dodson.personalfinance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileDTO {

	@JsonProperty
	String JWTToken;

	@JsonProperty
	UserDTO userDTO;

	public String getJWTToken() {
		return JWTToken;
	}

	public void setJWTToken(String jWTToken) {
		this.JWTToken = jWTToken;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
