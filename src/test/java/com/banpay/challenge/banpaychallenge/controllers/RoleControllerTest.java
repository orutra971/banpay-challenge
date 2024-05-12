package com.banpay.challenge.banpaychallenge.controllers;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.payload.request.LoginRequest;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleControllerTest {

	private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	private final String adminUsername = "orutra971";
	private final String adminPassword = "Fx97dxdyaLL!";
	private final String adminEmail = "orutra971@hotmail.com";

	private final String testUsername = "aguacate";
	private final String testPassword = "Oaxaca06";
	private final String testEmail = "arturo.info2@gmail.com";

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
	 * Retrieves all films from the server and verifies the response.
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Calls the getCsrf() method to retrieve the CSRF token from the server.</li>
	 *   <li>Creates a SignupRequest object with the test credentials and sets the role to "user" and "films".</li>
	 *   <li>Calls the signupWithCredential() method to sign up a user with the provided credentials.</li>
	 *   <li>Calls the signinWithCredential() method to sign in with the test username and password.</li>
	 *   <li>Calls the getStudioGhibleData() method with the CSRF token, authorization token, and "films" role to retrieve all films.</li>
	 *   <li>Verifies that the response status code is HttpStatus.OK.value() (200).</li>
	 *   <li>Verifies that the response body "message" property is "All films obtained".</li>
	 * </ol>
	 * 
	 * @see SignupRequest
	 * @see #getCsrf()
	 * @see #signupWithCredential(String, SignupRequest)
	 * @see #signinWithCredential(String, String, String)
	 * @see #getStudioGhibleData(String, String, String)
	 */
	@Test
	void getAllFilms() {
		System.out.println("ROLE CONTROLLER TEST: getAllFilms");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(testUsername);
		signupRequest.setEmail(testEmail);
		signupRequest.setPassword(testPassword);
		signupRequest.setRole(Set.of("user", "films"));

		String csrfToken = getCsrf();
		signupWithCredential(getCsrf(), signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, testUsername, testPassword);
		getStudioGhibleData(csrfToken, authorizationToken, "films")
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("message", is("All films obtained"));
	}

	/**
	 * Retrieves all people from the server and verifies the response.
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Creates a SignupRequest object with the test credentials and sets the role to "user" and "people".</li>
	 *   <li>Calls the getCsrf() method to retrieve the CSRF token from the server.</li>
	 *   <li>Calls the signupWithCredential() method to sign up a user with the provided credentials.</li>
	 *   <li>Calls the signinWithCredential() method to sign in with the test username and password.</li>
	 *   <li>Calls the getStudioGhibleData() method with the CSRF token, authorization token, and "people" role to retrieve all people.</li>
	 *   <li>Verifies that the response status code is HttpStatus.OK.value() (200).</li>
	 *   <li>Verifies that the response body "message" property is "All people obtained".</li>
	 * </ol>
	 * 
	 * @see SignupRequest
	 * @see #getCsrf()
	 * @see #signupWithCredential(String, SignupRequest)
	 * @see #signinWithCredential(String, String, String)
	 * @see #getStudioGhibleData(String, String, String)
	 */
	@Test
	void getAllPeople() {
		System.out.println("ROLE CONTROLLER TEST: getAllPeople");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(testUsername);
		signupRequest.setEmail(testEmail);
		signupRequest.setPassword(testPassword);
		signupRequest.setRole(Set.of("user", "people"));

		String csrfToken = getCsrf();
		signupWithCredential(getCsrf(), signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, testUsername, testPassword);
		getStudioGhibleData(csrfToken, authorizationToken, "people")
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("message", is("All people obtained"));
	}

	/**
	 * Retrieves all locations from the server and verifies the response.
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Creates a SignupRequest object with the test credentials and sets the role to "user" and "locations".</li>
	 *   <li>Calls the getCsrf() method to retrieve the CSRF token from the server.</li>
	 *   <li>Calls the signupWithCredential() method to sign up a user with the provided credentials.</li>
	 *   <li>Calls the signinWithCredential() method to sign in with the test username and password.</li>
	 *   <li>Calls the getStudioGhibleData() method with the CSRF token, authorization token, and "locations" role to retrieve all locations.</li>
	 *   <li>Verifies that the response status code is HttpStatus.OK.value() (200).</li>
	 *   <li>Verifies that the response body "message" property is "All locations obtained".</li>
	 * </ol>
	 * 
	 * @see SignupRequest
	 * @see #getCsrf()
	 * @see #signupWithCredential(String, SignupRequest)
	 * @see #signinWithCredential(String, String, String)
	 * @see #getStudioGhibleData(String, String, String)
	 */
	@Test
	void getAllLocations() {
		System.out.println("ROLE CONTROLLER TEST: getAllLocations");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(testUsername);
		signupRequest.setEmail(testEmail);
		signupRequest.setPassword(testPassword);
		signupRequest.setRole(Set.of("user", "locations"));

		String csrfToken = getCsrf();
		signupWithCredential(getCsrf(), signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, testUsername, testPassword);
		getStudioGhibleData(csrfToken, authorizationToken, "locations")
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("message", is("All locations obtained"));
	}

	/**
	 * Retrieves all species from the server and verifies the response.
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Creates a SignupRequest object with the test credentials and sets the role to "user" and "species".</li>
	 *   <li>Calls the getCsrf() method to retrieve the CSRF token from the server.</li>
	 *   <li>Calls the signupWithCredential() method to sign up a user with the provided credentials.</li>
	 *   <li>Calls the signinWithCredential() method to sign in with the test username and password.</li>
	 *   <li>Calls the getStudioGhibleData() method with the CSRF token, authorization token, and "species" role to retrieve all species.</li>
	 *   <li>Verifies that the response status code is HttpStatus.OK.value() (200).</li>
	 *   <li>Verifies that the response body "message" property is "All species obtained".</li>
	 * </ol>
	 * 
	 * @see SignupRequest
	 * @see #getCsrf()
	 * @see #signupWithCredential(String, SignupRequest)
	 * @see #signinWithCredential(String, String, String)
	 * @see #getStudioGhibleData(String, String, String)
	 */
	@Test
	void getAllSpecies() {
		System.out.println("ROLE CONTROLLER TEST: getAllSpecies");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(testUsername);
		signupRequest.setEmail(testEmail);
		signupRequest.setPassword(testPassword);
		signupRequest.setRole(Set.of("user", "species"));

		String csrfToken = getCsrf();
		signupWithCredential(getCsrf(), signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, testUsername, testPassword);
		getStudioGhibleData(csrfToken, authorizationToken, "species")
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("message", is("All species obtained"));
	}

	/**
	 * Retrieves all vehicles from the server and verifies the response.
	 * <p>
	 * This method performs the following steps:
	 * </p>
	 * <ol>
	 *   <li>Creates a SignupRequest object with the test credentials and sets the role to "user" and "vehicles".</li>
	 *   <li>Calls the getCsrf() method to retrieve the CSRF token from the server.</li>
	 *   <li>Calls the signupWithCredential() method to sign up a user with the provided credentials.</li>
	 *   <li>Calls the signinWithCredential() method to sign in with the test username and password.</li>
	 *   <li>Calls the getStudioGhibleData() method with the CSRF token, authorization token, and "vehicles" role to retrieve all vehicles.</li>
	 *   <li>Verifies that the response status code is HttpStatus.OK.value() (200).</li>
	 *   <li>Verifies that the response body "message" property is "All vehicles obtained".</li>
	 * </ol>
	 * 
	 * @see SignupRequest
	 * @see #getCsrf()
	 * @see #signupWithCredential(String, SignupRequest)
	 * @see #signinWithCredential(String, String, String)
	 * @see #getStudioGhibleData(String, String, String)
	 */
	@Test
	void getAllVehicles() {
		System.out.println("ROLE CONTROLLER TEST: getAllVehicles");

		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setUsername(testUsername);
		signupRequest.setEmail(testEmail);
		signupRequest.setPassword(testPassword);
		signupRequest.setRole(Set.of("user", "vehicles"));

		String csrfToken = getCsrf();
		signupWithCredential(getCsrf(), signupRequest);
		String authorizationToken = signinWithCredential(csrfToken, testUsername, testPassword);
		getStudioGhibleData(csrfToken, authorizationToken, "vehicles")
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK.value())
				.body("message", is("All vehicles obtained"));
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
	 * @param csrfToken     The CSRF token needed for authentication.
	 * @param signupRequest The request object containing the user credentials.
	 */
	public void signupWithCredential(String csrfToken, SignupRequest signupRequest) {
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

	public Response getStudioGhibleData(String csrfToken, String authorizationToken, String userRole) {
		return given()
				.contentType(ContentType.JSON)
				.header("X-XSRF-TOKEN", csrfToken)  // Provide CSRF as header
				.header("Authorization", authorizationToken)
				.cookie("XSRF-TOKEN", csrfToken)   // And as a
				.when()
				.get("/api/role/" + userRole);
	}

}