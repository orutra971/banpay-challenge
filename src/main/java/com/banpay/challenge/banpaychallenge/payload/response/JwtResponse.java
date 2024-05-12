package com.banpay.challenge.banpaychallenge.payload.response;

import java.util.List;

/**
 * This class represents a JWT (JSON Web Token) Response within the application.
 * Each JwtResponse instance contains properties such as token type, token, id, username, email, and roles.
 */
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private final List<String> roles;

	/**
	 * Constructor for the JwtResponse class.
	 *
	 * @param accessToken the access token
	 * @param id  the id
	 * @param username the username
	 * @param email the email
	 * @param roles the roles
	 */
	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	/**
	 * Gets the Access Token of this JwtResponse.
	 *
	 * @return the Access Token
	 */
	public String getAccessToken() {
		return token;
	}

	/**
	 * Sets the Access Token of this JwtResponse.
	 *
	 * @param accessToken the new Access Token
	 */
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	/**
	 * Gets the Token Type of this JwtResponse.
	 *
	 * @return the Token Type
	 */
	public String getTokenType() {
		return type;
	}

	/**
	 * Sets the Token Type of this JwtResponse.
	 *
	 * @param tokenType the new Token Type
	 */
	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	/**
	 * Gets the ID of this JwtResponse.
	 *
	 * @return the ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of this JwtResponse.
	 *
	 * @param id the new ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the Email of this JwtResponse.
	 *
	 * @return the Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the Email of this JwtResponse.
	 *
	 * @param email the new Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the Username of this JwtResponse.
	 *
	 * @return the Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the Username of this JwtResponse.
	 *
	 * @param username the new Username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the Roles of this JwtResponse.
	 *
	 * @return the Roles
	 */
	public List<String> getRoles() {
		return roles;
	}
}
