package com.banpay.challenge.banpaychallenge.security.exception;

import com.banpay.challenge.banpaychallenge.payload.response.MessageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

/**
 * This class handles exceptions thrown during the processing of REST API requests and generates appropriate response entities.
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles an invalid method argument exception.
	 * This method is capable of handling `MethodArgumentNotValidException` thrown when the validation of arguments fails.
	 * The method collects the error messages from each failed validation constraint and includes them in the response body.
	 *
	 * @param ex the exception that was thrown when method argument validation failed
	 * @param headers HTTP headers for the returning response
	 * @param status the status code for the HTTP response
	 * @param request the original web request
	 * @return a `ResponseEntity` containing error information
	 */
	@Override
	protected  ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status,
			WebRequest request) {
		ArrayList<String> errors = new ArrayList<>();

		if (ex.getBindingResult().hasErrors()) {
			for (FieldError error : ex.getBindingResult().getFieldErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return ResponseEntity.badRequest().body(new MessageResponse<>("Incorrect petition", errors));
		}

		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}
}
