package com.banpay.challenge.banpaychallenge.payload.request;

import jakarta.validation.constraints.*;

import java.util.Set;

/**
 * This class represents a Signup Request within the application.
 * Each SignupRequest instance contains properties such as username, password, email, and roles.
 * The class includes validation annotations to ensure these fields are not blank and that they meet the specified size requirements.
 */
public class SignupRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	/**
	 * Gets the username of this SignupRequest.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of this SignupRequest.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the email of this SignupRequest.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of this SignupRequest.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password of this SignupRequest.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of this SignupRequest.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the roles of this SignupRequest.
	 *
	 * @return the roles as a Set of Strings
	 */
	public Set<String> getRole() {
		return this.role;
	}

	/**
	 * Sets the roles of this SignupRequest.
	 *
	 * @param role the new roles as a Set of Strings
	 */
	public void setRole(Set<String> role) {
		this.role = role;
	}
}
