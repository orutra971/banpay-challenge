package com.banpay.challenge.banpaychallenge.controllers;

import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.payload.request.LoginRequest;
import com.banpay.challenge.banpaychallenge.payload.request.SignupRequest;
import com.banpay.challenge.banpaychallenge.payload.response.JwtResponse;
import com.banpay.challenge.banpaychallenge.payload.response.MessageResponse;
import com.banpay.challenge.banpaychallenge.security.jwt.JwtUtils;
import com.banpay.challenge.banpaychallenge.security.services.UserDetailsImpl;
import com.banpay.challenge.banpaychallenge.security.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * AuthController is a REST API for managing user authentication and registration.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	/**
	 * Object that handles authentication tasks.
	 */
	private final AuthenticationManager authenticationManager;
	/**
	 * Service responsible for all user-related tasks.
	 */
	private final UserService userService;
	/**
	 * Used for encoding user passwords.
	 */
	private final PasswordEncoder encoder;
	/**
	 * Utility class for generating and validating JWT tokens.
	 */
	private final JwtUtils jwtUtils;

	/**
	 * This constructor is to be used for Dependency Injection.
	 *
	 * @param authenticationManager Handles authentication tasks
	 * @param userService           Service layer for all user-related tasks
	 * @param encoder               Password encoder
	 * @param jwtUtils              Jwt utility class
	 */
	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
						  UserService userService,
						  PasswordEncoder encoder,
						  JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	/**
	 * Authenticates a user and generates a JWT token.
	 *
	 * @param loginRequest Request object that includes username and password to be authenticated
	 * @return ResponseEntity containing the JwtResponse object which includes the JWT token
	 */
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		LOGGER.info("Authenticating user: {}", loginRequest);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.id(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));
	}

	/**
	 * Registers a new user in the system.
	 *
	 * @param signUpRequest Request object that includes new user details
	 * @return ResponseEntity containing the MessageResponse object which includes the registration message and the registered user object
	 */
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse<User>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		LOGGER.info("Registering user: {}", signUpRequest);
		if (userService.existsUserByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse<>("Error: Username is already taken!"));
		}

		if (userService.existsUserByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse<>("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = Optional.ofNullable(signUpRequest.getRole()).orElse(new HashSet<>());

		userService.assignRolesToUser(user, strRoles, false);
		user = userService.createUser(user);
		user.setPassword("");

		return ResponseEntity.ok(new MessageResponse<>("User registered successfully!", user));
	}
}
