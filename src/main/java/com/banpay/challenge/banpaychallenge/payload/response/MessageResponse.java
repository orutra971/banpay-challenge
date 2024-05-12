package com.banpay.challenge.banpaychallenge.payload.response;

import java.util.UUID;

/**
 * This class represents a generic Message Response within the application.
 * Each MessageResponse instance contains a folio, a message, and data of a generic type.
 * The class provides abstractions to handle messages and data in a standardized way across the application.
 */
public class MessageResponse <T> {

	private final UUID folio = UUID.randomUUID();
	private String message;
	private T data;

	/**
	 * Constructor for MessageResponse with message parameter.
	 *
	 * @param message the message
	 */
	public MessageResponse(String message) {
		this.message = message;
	}

	/**
	 * Constructor for MessageResponse with message and data parameters.
	 *
	 * @param message the message
	 * @param data the data
	 */
	public MessageResponse(String message, T data) {
		this.message = message;
		this.data = data;
	}

	/**
	 * Gets the folio of this MessageResponse.
	 *
	 * @return the folio as UUID
	 */
	public UUID getFolio() {
		return folio;
	}

	/**
	 * Gets the message of this MessageResponse.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of this MessageResponse.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the data of this MessageResponse.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data of this MessageResponse.
	 *
	 * @param data the new data
	 */
	public void setData(T data) {
		this.data = data;
	}
}
