package com.banpay.challenge.banpaychallenge.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CsrfController class, annotated with @RestController, provides an API endpoint for generating and retrieving CSRF tokens.
 * CSRF stands for Cross-Site Request Forgery, a type of attack that tricks the victim into submitting a malicious request.
 */
@RestController
public class CsrfController {

	/**
	 * Logger instance for logging events, info, and errors.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CsrfController.class);
	/**
	 * This method is exposed as a GET endpoint on path "/csrf" and is used to create a CSRF token.
	 * It also logs the creation of the CSRF token.
	 *
	 * @param csrfToken CSRF token to be generated
	 * @return Generated CsrfToken object
	 */
	@GetMapping("/csrf")
	public CsrfToken csrf(CsrfToken csrfToken) {
		LOGGER.info("Csrf Token created: {}", csrfToken.getToken());
		return csrfToken;
	}
}
