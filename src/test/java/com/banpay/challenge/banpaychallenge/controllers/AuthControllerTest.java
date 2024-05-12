package com.banpay.challenge.banpaychallenge.controllers;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.User;
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
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


/**
 * The AuthControllerTest class is responsible for testing the functionality of the AuthController class.
 * It contains test cases for signing up with an existing username and an existing email.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

	private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

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

		userRepository.save(user);
	}

	/**
	 * Test case for signing up with an existing username that should return a BAD_REQUEST response.
	 * The test verifies that the response status code is set to BAD_REQUEST (400) and the message in the response body is "Error: Username is already taken!".
	 */
	@Test
	@Order(1)
	void testSignUpWithExistingUsernameBadRequest() {
		System.out.println("AUTH CONTROLLER TEST: testSignUpWithExistingUsernameBadRequest");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(adminUsername);
		signupRequest.setPassword(adminPassword);
		signupRequest.setEmail("arturo.info2@gmail.com");

		String csrf = getCsrf();
		signupWithCredential(csrf, signupRequest)
				.then()
				.assertThat()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("message", is("Error: Username is already taken!"));
	}

	/**
	 * This method tests the signup functionality when trying to use an existing email.
	 * It verifies that the response status code is set to BAD_REQUEST (400) and the message in the response body is "Error: Email is already taken!".
	 */
	@Test
	@Order(2)
	void testSignUpWithExistingEmailBadRequest() {
		System.out.println("AUTH CONTROLLER TEST: testSignUpWithExistingUsernameBadRequest");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername("aguacate");
		signupRequest.setPassword(adminPassword);
		signupRequest.setEmail(adminEmail);

		String csrf = getCsrf();
		signupWithCredential(csrf, signupRequest)
				.then()
				.assertThat()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("message", is("Error: Email is already in use!"));
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
	 * Signup with credential.
	 *
	 * @param csrfToken The CSRF token to be used as a header and cookie value.
	 * @param signupRequest The SignupRequest object containing the username, password, email, and roles of the user.
	 * @return The response of the signup request.
	 */
	public Response signupWithCredential(String csrfToken, SignupRequest signupRequest) {
		return given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.body(signupRequest)
				.when()
				.post("/api/auth/signup");
	}
}