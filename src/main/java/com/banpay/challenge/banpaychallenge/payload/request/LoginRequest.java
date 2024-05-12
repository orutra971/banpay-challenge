package com.banpay.challenge.banpaychallenge.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * This class represents a Login Request within the application.
 * Each LoginRequest instance contains properties such as username and password.
 * The class includes validation annotations to ensure these fields are not blank.
 */
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the username property of this LoginRequest.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username property of this LoginRequest.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password property of this LoginRequest.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password property of this LoginRequest.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
