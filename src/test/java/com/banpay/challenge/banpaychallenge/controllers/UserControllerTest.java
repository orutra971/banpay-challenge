package com.banpay.challenge.banpaychallenge.controllers;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.payload.request.LoginRequest;
import com.banpay.challenge.banpaychallenge.payload.request.ModifyUserRequest;
import com.banpay.challenge.banpaychallenge.payload.request.SignupRequest;
import com.banpay.challenge.banpaychallenge.repository.UserRepository;
import com.banpay.challenge.banpaychallenge.security.services.RoleService;
import com.banpay.challenge.banpaychallenge.security.services.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * This is a test class for testing user controller functionality. It checks the actions of the user controller class under different scenarios such as getting CSRF, signing up with user credentials,
 * signing in with admin credentials, getting all users, modifying a non-existing user, modifying user username, modifying user email, modifying user password, modifying user assign all roles,
 * deleting a user, and deleting all users.
 * It uses PostgreSQL container for the test and deletes all users before each test starts. It also has a setup method to make sure the required settings are in place.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

	private static final  PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	private Long adminId;
	private final String adminUsername = "orutra971";
	private final String adminPassword = "Fx97dxdyaLL!";
	private final String adminEmail = "orutra971@hotmail.com";

	@LocalServerPort
	private Integer port;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	/**
	 * This method configures the properties of the PostgreSQL container connection for testing purposes.
	 */
	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	/**
	 * This method starts the PostgreSQL container before all tests.
	 */
	@BeforeAll
	public static void beforeAll() {
		postgres.start();
	}

	/**
	 * This method stops the PostgreSQL container after all tests.
	 */
	@AfterAll
	public static void afterAll() {
		postgres.stop();
	}

	/**
	 * This method sets up the test environment before each test.
	 */
	@BeforeEach
	public void setup() {
		RestAssured.baseURI = "http://localhost:" + port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.config().getCsrfConfig().csrfTokenPath(RestAssured.baseURI + "/csrf");

		userService.deleteAllUsers();

		User user = new User();
		user.setUsername(adminUsername);
		user.setEmail(adminEmail);
		user.setPassword(passwordEncoder.encode(adminPassword));
		user.setRoles(Set.of(roleService.getRole(ERole.ROLE_ADMIN)));

		adminId = userRepository.save(user).getId();
	}


	/**
	 * Retrieves the CSRF token from the server.
	 */
	@Test
	@Order(1)
	void testGetCsrf() {
		System.out.println("USER CONTROLLER TEST: testGetCsrf");
		getCsrf();
	}

	/**
	 * This method is a test case for signing up a user with user credentials.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Create a {@link SignupRequest} object with the necessary user credentials.</li>
	 *   <li>Obtain the CSRF token.</li>
	 *   <li>Sign up a new user using the {@code signupRequest} object.</li>
	 * </ol>
	 *
	 * @see SignupRequest
	 */
	@Test
	@Order(2)
	void testSignUpWithUserCredential() {
		System.out.println("USER CONTROLLER TEST: testSignUpWithUserCredential");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("user"));

		String csrfToken = getCsrf();
		signupWithCredential(csrfToken, signupRequest);
	}

	/**
	 * Test method for signing in with admin credentials.
	 * This method tests the sign-in functionality of the application using admin credentials.
	 *
	 * <p>
	 * It performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Retrieves the CSRF token.</li>
	 *   <li>Signs in using the admin username and password.</li>
	 * </ol>
	 */
	@Test
	@Order(3)
	void testSignInWithAdminCredential() {
		System.out.println("USER CONTROLLER TEST: testSignInWithAdminCredential");

		String csrfToken = getCsrf();
		signinWithCredential(csrfToken, adminUsername, adminPassword);
	}

	/**
	 * This method is used to test the functionality of getting all users.
	 * <p>
	 * It performs the following actions:
	 * </p>
	 * <ol>
	 *   <li>Obtains the CSRF token.</li>
	 *   <li>Signs in with the admin's credentials using the obtained CSRF token.</li>
	 *   <li>Calls the getAllUsers method with the obtained CSRF token, auth token, and a page number.</li>
	 * </ol>
	 */
	@Test
	@Order(4)
	void testGetAllUsers() {
		System.out.println("USER CONTROLLER TEST: testGetAllUsers");

		String csrfToken = getCsrf();
		String authToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authToken,1);
	}

	/**
	 * Tests the method getUserWithId.
	 * <p>
	 * This test method performs the following actions:
	 * </p>
	 * <ol>
	 *   <li>Obtains a CSRF token using the method getCsrf().</li>
	 *   <li>Signs in as an administrator using the signinWithCredential() method with the provided credentials.</li>
	 *   <li>Calls the getUserById() method with the obtained CSRF token, authentication token, and the adminId parameter.</li>
	 *   <li>Asserts that the HTTP response status code is HttpStatus.OK (200).</li>
	 *   <li>Asserts that the response message is "User obtained successfully".</li>
	 *   <li>Asserts that the username in the response body matches the adminUsername parameter.</li>
	 *   <li>Asserts that the email in the response body matches the adminEmail parameter.</li>
	 * </ol>
	 */
	@Test
	@Order(5)
	void testGetUserWithId() {
		System.out.println("USER CONTROLLER TEST: testGetUserWithId");

		String csrfToken = getCsrf();
		String authToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getUserById(csrfToken, authToken, adminId.intValue())
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("message", is("User obtained successfully"))
				.body("data.username", is(adminUsername))
				.body("data.email", is(adminEmail));
	}


	/**
	 * This method tests the functionality of getting a user by id when the user is not found.
	 * <p>
	 * It performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Obtains a CSRF token from the server.</li>
	 *   <li>Signs in using the admin credentials.</li>
	 *   <li>Calls the getUserById method with a non-existent user id.</li>
	 *   <li>Verifies that the response status code is HttpStatus.NOT_FOUND (404).</li>
	 *   <li>Checks that the response body contains the message "User not found".</li>
	 * </ol>
	 */
	@Test
	@Order(6)
	void testGetUserWithIdNotFound() {
		System.out.println("USER CONTROLLER TEST: testGetUserWithIdNotFound");

		String csrfToken = getCsrf();
		String authToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getUserById(csrfToken, authToken, 100)
				.then()
				.assertThat()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.body("message", is("User not found"));
	}

	/**
	 * This method is a test case for modifying a non-existing user.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Create a {@link SignupRequest} object with the necessary user credentials.</li>
	 *   <li>Create a {@link ModifyUserRequest} object with the modified user details.</li>
	 *   <li>Obtain the CSRF token.</li>
	 *   <li>Sign up a new user using the {@code signupRequest} object.</li>
	 *   <li>Sign in as an admin user using the necessary credentials.</li>
	 *   <li>Attempt to modify the non-existing user with the {@code modifyUserRequest} object.</li>
	 *   <li>Verify that the response status code is HttpStatus.BAD_REQUEST (400).</li>
	 *   <li>Verify that the response body contains the error message "Error: User doesn't exist!".</li>
	 * </ol>
	 *
	 * @see SignupRequest
	 * @see ModifyUserRequest
	 */
	@Test
	@Order(7)
	void testModifyNonExistingUser() {
		System.out.println("USER CONTROLLER TEST: testModifyNonExistingUser");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setUsername("aguacate3");
		modifyUserRequest.setEmail("arturo.info3@gmail.com");
		modifyUserRequest.setPassword("Fx97dxdyaLL!");
		modifyUserRequest.setRole(Set.of("admin", "films", "people", "locations", "species", "vehicles"));

		String csrfToken = getCsrf();
		signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, 1280412, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.assertThat()
				.body("message", is("Error: User doesn't exists!"));
	}

	/**
	 * This method is a test case for modifying a user's username.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Create a {@link SignupRequest} object with the necessary user credentials.</li>
	 *   <li>Create a {@link ModifyUserRequest} object with the modified user details.</li>
	 *   <li>Obtain the CSRF token.</li>
	 *   <li>Sign up a new user using the {@code signupRequest} object.</li>
	 *   <li>Sign in as an admin user using the necessary credentials.</li>
	 *   <li>Attempt to modify the user with the given {@code userId} using the {@code modifyUserRequest} object.</li>
	 *   <li>Verify that the response status code is 200 (OK).</li>
	 *   <li>Verify that the response body contains the message "User modified successfully".</li>
	 * </ol>
	 *
	 * @see SignupRequest
	 * @see ModifyUserRequest
	 */
	@Test
	@Order(8)
	void testModifyUserUsername() {
		System.out.println("USER CONTROLLER TEST: testModifyUserUsername");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setUsername("aguacate3");

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User modified successfully"));
	}

	/**
	 * This test method checks the case when trying to modify a user's username with an existing username,
	 * which should result in a bad request.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Obtain the CSRF token</li>
	 *   <li>Sign up a new user with unique credentials.</li>
	 *   <li>Sign in as an admin user.</li>
	 *   <li>Attempt to modify the username of the newly created user to an existing username.</li>
	 *   <li>Verify that the response status code is 400 (Bad Request) and the response body contains
	 *       the error message "Error: Username is already taken!".</li>
	 * </ol>
	 */
	@Test
	@Order(9)
	void testModifyUserUsernameSameUsername() {
		System.out.println("USER CONTROLLER TEST: testModifyUserUsernameSameUsername");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setUsername("aguacate");

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User modified successfully"));
	}

	/**
	 * This method tests the case when trying to modify a user's username with an existing username,
	 * which should result in a bad request.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Obtain the CSRF token</li>
	 *   <li>Sign up a new user with unique credentials.</li>
	 * 	 <li>Sign in as an admin user.</li>
	 * 	 <li>Attempt to modify the username of the newly created user to an existing username.</li>
	 * 	 <li>Verify that the response status code is 400 (Bad Request) and the response body contains
	 *    	 the error message "Error: Username is already taken!".</li>
	 * </ol>
	 */
	@Test
	@Order(10)
	void testModifyUserUsernameExistingUsernameBadRequest() {
		System.out.println("USER CONTROLLER TEST: testModifyUserUsernameExistingUsernameBadRequest");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setUsername("orutra971");

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.assertThat()
				.body("message", is("Error: Username is already taken!"));
	}

	/**
	 * This method is a test case for modifying a user's email.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Create a {@link SignupRequest} object with the necessary user credentials.</li>
	 *   <li>Create a {@link ModifyUserRequest} object with the modified user details.</li>
	 *   <li>Obtain the CSRF token.</li>
	 *   <li>Sign up a new user using the {@code signupRequest} object.</li>
	 *   <li>Sign in as an admin user using the necessary credentials.</li>
	 *   <li>Attempt to modify the user with the given {@code userId} using the {@code modifyUserRequest} object.</li>
	 *   <li>Verify that the response status code is 200 (OK).</li>
	 *   <li>Verify that the response body contains the message "User modified successfully".</li>
	 * </ol>
	 *
	 * @see SignupRequest
	 * @see ModifyUserRequest
	 */
	@Test
	@Order(11)
	void testModifyUserEmail() {
		System.out.println("USER CONTROLLER TEST: testModifyUserEmail");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setEmail("arturo.info3@gmail.com");

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User modified successfully"));
	}

	/**
	 * This test method checks the case when trying to modify a user's email with an existing email,
	 * which should result in a bad request.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Obtain the CSRF token</li>
	 *   <li>Sign up a new user with unique credentials.</li>
	 *   <li>Sign in as an admin user.</li>
	 *   <li>Attempt to modify the username of the newly created user to an existing email.</li>
	 *   <li>Verify that the response status code is 400 (Bad Request) and the response body contains
	 *       the error message "Error: Email is already taken!".</li>
	 * </ol>
	 */
	@Test
	@Order(12)
	void testModifyUserEmailSameEmail() {
		System.out.println("USER CONTROLLER TEST: testModifyUserEmailSameEmail");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setEmail("arturo.info2@gmail.com");

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User modified successfully"));
	}

	/**
	 * This method tests the case when trying to modify a user's email with an existing email,
	 * which should result in a bad request.
	 *
	 * <p>
	 * The test follows these steps:
	 * </p>
	 * <ol>
	 *   <li>Obtain the CSRF token</li>
	 *   <li>Sign up a new user with unique credentials.</li>
	 * 	 <li>Sign in as an admin user.</li>
	 * 	 <li>Attempt to modify the username of the newly created user to an existing username.</li>
	 * 	 <li>Verify that the response status code is 400 (Bad Request) and the response body contains
	 *    	 the error message "Error: Email is already taken!".</li>
	 * </ol>
	 */
	@Test
	@Order(13)
	void testModifyUserEmailExistingEmailBadRequest() {
		System.out.println("USER CONTROLLER TEST: testModifyUserEmailExistingEmailBadRequest");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setEmail(adminEmail);

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.assertThat()
				.body("message", is("Error: Email is already in use!"));
	}

	/**
	 * This method is a test case for the modification of a user's password.
	 * <p>
	 * The test case performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Creates a new SignupRequest object with the username, email, password, and role.</li>
	 *   <li>Creates a new ModifyUserRequest object with the new password.</li>
	 *   <li>Retrieves the CSRF token required for authentication.</li>
	 *   <li>Signs up a new user with the provided credentials and retrieves the user ID.</li>
	 *   <li>Signs in using the admin credentials and retrieves the authorization token.</li>
	 *   <li>Retrieves all users with the specified role.</li>
	 *   <li>Modifies the password of the user with the provided ID using the modifyUser method.</li>
	 *   <li>Verifies that the response status code is HTTP 200 OK.</li>
	 *   <li>Asserts that the response body contains the message "User modified successfully".</li>
	 * </ol>
	 */
	@Test
	@Order(14)
	void testModifyUserPassword() {
		System.out.println("USER CONTROLLER TEST: testModifyUserPassword");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setPassword("Fx97dxdyaLL!!");

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User modified successfully"));
	}

	/**
	 * This method is a test case for modifying a user's assigned roles.
	 * <p>
	 * It performs the following actions:
	 * </p>
	 * <ol>
	 *   <li>Creates a new SignupRequest object with a username, email, password, and initial role.</li>
	 *   <li>Creates a new ModifyUserRequest object with a set of roles to assign to the user.</li>
	 *   <li>Retrieves a CSRF token.</li>
	 *   <li>Signs up a new user with the provided credentials and retrieves their user ID.</li>
	 *   <li>Signs in as an admin user and retrieves an authorization token.</li>
	 *   <li>Retrieves all users.</li>
	 *   <li>Modifies the user's roles with the provided roles.</li>
	 *   <li>Asserts that the HTTP response status code is 200 (OK).</li>
	 *   <li>Asserts that the response body contains the message "User modified successfully".</li>
	 * </ol>
	 */
	@Test
	@Order(15)
	void testModifyUserAssignAllRoles() {
		System.out.println("USER CONTROLLER TEST: testModifyUserAssignAllRoles");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("admin"));

		ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
		modifyUserRequest.setRole(Set.of("admin", "films", "people", "locations", "species", "vehicles"));

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		modifyUser(csrfToken, authorizationToken, userId, modifyUserRequest)
				.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User modified successfully"));
	}

	/**
	 * Method to test the functionality of deleting a user.
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 *   <li>Creates a new user with the given signup information.</li>
	 *   <li>Retrieves the CSRF token.</li>
	 *   <li>Signs in with the admin credentials and retrieves the authorization token.</li>
	 *   <li>Retrieves the list of all users, before deleting the user created in step 1.</li>
	 *   <li>Retrieves the list of all users again, after deleting the user.</li>
	 * </p>
	 */
	@Test
	@Order(16)
	void testDeleteUser() {
		System.out.println("USER CONTROLLER TEST: testDeleteUser");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setEmail("arturo.info2@gmail.com");
		signupRequest.setPassword("Fx97dxdyaLL!");
		signupRequest.setRole(Set.of("user"));

		String csrfToken = getCsrf();
		int userId = signupWithCredential(csrfToken, signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,2);
		deleteUser(csrfToken, authorizationToken, userId);
		getAllUsers(csrfToken, authorizationToken, 1);
	}

	/**
	 * This method tests the functionality of deleting all users.
	 * <p>
	 * It performs the following steps:
	 * </p>
	 * <ol>
	 *   </li>Retrieves the CSRF token from the server.</li>
	 *   </li>Signs in with the admin credentials using the retrieved CSRF token.</li>
	 *   </li>Retrieves all users from the server using the CSRF token and authorization token.</li>
	 *   </li>Deletes all users from the server using the CSRF token and authorization token.</li>
	 * </ol>
	 */
	@Test
	@Order(17)
	void testDeleteAllUsers() {
		System.out.println("USER CONTROLLER TEST: testDeleteAllUsers");
		String csrfToken = getCsrf();
		String authorizationToken = signinWithCredential(csrfToken, adminUsername, adminPassword);
		getAllUsers(csrfToken, authorizationToken,1);
		deleteAllUSers(csrfToken, authorizationToken);
	}

	/**
	 * Retrieves the CSRF token from the server.
	 *
	 * @return The CSRF token.
	 */
	public String getCsrf() {
		// First, make a GET request to fetch CSRF token
		Response csrf = given().contentType(ContentType.JSON).when().get("/csrf");
		// Extract CSRF token from the GET response cookies
		String csrfToken = csrf.cookie("XSRF-TOKEN");

		csrf.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("headerName", is("X-XSRF-TOKEN"))
				.body("parameterName", is("_csrf"));
		return csrfToken;
	}

	/**
	 * Sign up a user with the provided credentials.
	 *
	 * @param csrfToken      The CSRF token needed for aut
		System.out.println("USER CONTROLLER TEST: testDeleteAllUsers");hentication.
	 * @param signupRequest  The request object containing the user credentials.
	 * @return The ID of the signed-up user.
	 */
	public int signupWithCredential(String csrfToken, SignupRequest signupRequest) {
		Response auth = given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.body(signupRequest)
				.when()
				.post("/api/auth/signup");

		auth.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("data.username", is(signupRequest.getUsername()))
				.body("message", is("User registered successfully!"));

		return auth.getBody().jsonPath().getInt("data.id");
	}

	/**
	 * Sign in with the provided username and password credentials.
	 *
	 * @param csrfToken The CSRF token needed for authentication.
	 * @param username  The username of the user.
	 * @param password  The password of the user.
	 * @return The access token obtained upon successful sign in.
	 */
	public String signinWithCredential(String csrfToken, String username, String password) {
		LoginRequest loginRequest = new LoginRequest(username, password);
		Response auth = given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.body(loginRequest)
				.when()
				.post("/api/auth/signin");

		auth.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("username", is(username));

		return auth.getBody().jsonPath().get("accessToken");
	}

	/**
	 * Retrieves all users from the server.
	 *
	 * @param csrfToken           The CSRF token needed for authentication.
	 * @param authorizationToken  The authorization token needed for authentication.
	 * @param numberOfUsers       The expected number of users to be retrieved.
	 */
	public void getAllUsers(String csrfToken, String authorizationToken, int numberOfUsers) {
		Response users = given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.header("Authorization", "Bearer " + authorizationToken)
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.when()
				.get("/api/users");

		users.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("All users obtained"))
				.body("data.size()", is(numberOfUsers))
				.body("data[0].username", is("orutra971"))
				.body("data[0].email", is("orutra971@hotmail.com"));
	}

	/**
	 * Retrieves a user by their user ID.
	 *
	 * @param csrfToken           The CSRF token value.
	 * @param authorizationToken  The authorization token value.
	 * @param userId              The ID of the user to retrieve.
	 * @return The response object containing the user details.
	 */
	public Response getUserById(String csrfToken, String authorizationToken, int userId) {
		return given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.header("Authorization", "Bearer " + authorizationToken)
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.when()
				.get("/api/users/" + userId);
	}

	/**
	 * Modifies a user.
	 *
	 * @param csrfToken           The CSRF token needed for authentication.
	 * @param authorizationToken  The authorization token needed for authentication.
	 * @param userId              The ID of the user to be modified.
	 * @param modifyUserRequest   The request object containing the modified user details.
	 * @return The response object containing the result of the modification.
	 */
	public Response modifyUser(String csrfToken, String authorizationToken, int userId, ModifyUserRequest modifyUserRequest) {
		return given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.header("Authorization", "Bearer " + authorizationToken)
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.body(modifyUserRequest)
				.when()
				.patch("/api/users/" + userId);
	}

	/**
	 * Deletes a user.
	 *
	 * @param csrfToken           The CSRF token needed for authentication.
	 * @param authorizationToken The authorization token needed for authentication.
	 * @param userId             The ID of the user to be deleted.
	 */
	public void deleteUser(String csrfToken, String authorizationToken, int userId) {
		Response deletedUser = given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.header("Authorization", "Bearer " + authorizationToken)
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.when()
				.delete("/api/users/" + userId);

		deletedUser.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("User deleted successfully"));
	}

	/**
	 * Deletes all users.
	 *
	 * @param csrfToken           The CSRF token needed for authentication.
	 * @param authorizationToken The authorization token needed for authentication.
	 */
	public void deleteAllUSers(String csrfToken, String authorizationToken) {
		Response deletedUsers = given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.header("Authorization", "Bearer " + authorizationToken)
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.when()
				.delete("/api/users");

		deletedUsers.then()
				.statusCode(HttpStatus.OK.value())
				.assertThat()
				.body("message", is("All users deleted successfully"));
	}

}
