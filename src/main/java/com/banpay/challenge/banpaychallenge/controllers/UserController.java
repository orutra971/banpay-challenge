package com.banpay.challenge.banpaychallenge.controllers;

import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.payload.request.ModifyUserRequest;
import com.banpay.challenge.banpaychallenge.payload.response.MessageResponse;
import com.banpay.challenge.banpaychallenge.security.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * The UserController class serves HTTP requests related to users in the system.
 * It is annotated as RestController to handle RESTful web services and is linked to the "/api/users" path.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	/**
	 * Logger instance for logging events, info, and errors.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	/**
	 * UserService instance to handle user operations.
	 */
	private final UserService userService;
	/**
	 * PasswordEncoder instance for password handling.
	 */
	private final PasswordEncoder encoder;

	/**
	 * Autowires UserService and PasswordEncoder.
	 *
	 * @param userService UserService to handle user operations.
	 * @param encoder     PasswordEncoder to handle password operations.
	 */
	@Autowired
	public UserController(UserService userService, PasswordEncoder encoder) {
		this.userService = userService;
		this.encoder = encoder;
	}

	/**
	 * Returns all the users from the system.
	 *
	 * @return List of users.
	 */
	@GetMapping
	public ResponseEntity<MessageResponse<List<User>>> getAllUsers() {
		LOGGER.info("Getting all users");
		return new ResponseEntity<>(new MessageResponse<>("All users obtained", userService.findAllUsers()),
									HttpStatus.OK);
	}

	/**
	 * Retrieves a User with the specified id.
	 *
	 * @param id The id of the User to retrieve.
	 * @return A ResponseEntity containing a MessageResponse with the retrieved User or an error message.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<MessageResponse<User>> getUser(@PathVariable Long id) {
		LOGGER.info("Getting user with id {}", id);
		Optional<User> user = userService.findUserById(id);

		return user
				.map(value -> new ResponseEntity<>(new MessageResponse<>("User obtained successfully", value),
												   HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(new MessageResponse<>("User not found", null),
													  HttpStatus.NOT_FOUND));

	}

	/**
	 * Handles delete user requests to the specified id.
	 *
	 * @param id the id of the user.
	 * @return The response entity with status and message.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse<String>> deleteUser(@PathVariable Long id) {
		LOGGER.info("Deleting user with id: {}", id);
		userService.deleteUserById(id);
		return new ResponseEntity<>(new MessageResponse<>("User deleted successfully"), HttpStatus.OK);
	}

	/**
	 * Deletes all the users from the system.
	 *
	 * @return ResponseEntity with a delete message.
	 */
	@DeleteMapping
	public ResponseEntity<MessageResponse<String>> deleteAllUsers() {
		LOGGER.info("Deleting all users");
		userService.deleteAllUsers();
		return new ResponseEntity<>(new MessageResponse<>("All users deleted successfully"), HttpStatus.OK);
	}

	/**
	 * Modifies a user with the specified id.
	 *
	 * @param id                ID of the user.
	 * @param modifyUserRequest Request body with updated user info.
	 * @return ResponseEntity with updated user data.
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<MessageResponse<User>> modifyUser(@PathVariable Long id, @Valid @RequestBody ModifyUserRequest modifyUserRequest) {
		LOGGER.info("Modifying user with id: {}", id);
		Optional<User> user = userService.findUserById(id);

		if (user.isEmpty()) {
			return ResponseEntity.badRequest().body(new MessageResponse<>("Error: User doesn't exists!"));
		}

		User upatedUser = user.get();

		if (!Objects.isNull(modifyUserRequest.getUsername()) &&
			!user.get().getUsername().equals(modifyUserRequest.getUsername())) {
			if (userService.existsUserByUsername(modifyUserRequest.getUsername())) {
				return ResponseEntity.badRequest().body(new MessageResponse<>("Error: Username is already taken!"));
			}
			upatedUser.setUsername(modifyUserRequest.getUsername());
		}

		if (!Objects.isNull(modifyUserRequest.getEmail()) &&
			!user.get().getEmail().equals(modifyUserRequest.getEmail())) {
			if (userService.existsUserByEmail(modifyUserRequest.getEmail())) {
				return ResponseEntity.badRequest().body(new MessageResponse<>("Error: Email is already in use!"));
			}
			upatedUser.setEmail(modifyUserRequest.getEmail());
		}

		if (!Objects.isNull(modifyUserRequest.getPassword())) {
			upatedUser.setPassword(encoder.encode(modifyUserRequest.getPassword()));
		}

		if (!Objects.isNull(modifyUserRequest.getRole())) {
			Set<String> strRoles = Optional.ofNullable(modifyUserRequest.getRole()).orElse(new HashSet<>());
			userService.assignRolesToUser(upatedUser, strRoles, true);
		}

		User savedUser = userService.saveUser(upatedUser);
		return ResponseEntity.ok(new MessageResponse<>("User modified successfully", savedUser));
	}
}
