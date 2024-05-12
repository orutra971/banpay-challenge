package com.banpay.challenge.banpaychallenge.payload.request;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * This class represents a request to modify a user within the application.
 * Each ModifyUserRequest instance includes information such as username, email, password, and roles associated with the user.
 */
public class ModifyUserRequest {

	@Size(min = 3, max = 20)
	@Pattern(message="Username must be between 3 and 20 characters and not contain spaces", regexp="^.{3,20}$")
	@Nullable
	private String username;

	@Size(max = 50)
	@Email
	//@Pattern(message="Email must be less than 50 characters", regexp="^.{3,50}$")
	@Nullable
	private String email;

	private Set<String> role;

	@Size(min = 6, max = 40)
	@Pattern(message="Password must be between 6 and 40 characters and not contain spaces", regexp="^.{6,40}$")
	@Nullable
	private String password;

	/**
	 * Gets the username of this user.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of this user.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the email of this user.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of this user.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the password of this user.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of this user.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the roles of this user.
	 *
	 * @return the roles
	 */
	public Set<String> getRole() {
		return this.role;
	}

	/**
	 * Sets the roles of this user.
	 *
	 * @param role roles to be associated with the user
	 */
	public void setRole(Set<String> role) {
		this.role = role;
	}
}
