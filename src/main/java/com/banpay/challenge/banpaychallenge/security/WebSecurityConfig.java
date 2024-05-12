package com.banpay.challenge.banpaychallenge.security;

import com.banpay.challenge.banpaychallenge.security.jwt.AuthEntryPointJwt;
import com.banpay.challenge.banpaychallenge.security.jwt.AuthTokenFilter;
import com.banpay.challenge.banpaychallenge.security.jwt.JwtUtils;
import com.banpay.challenge.banpaychallenge.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.client.RestTemplate;

/**
 * The WebSecurityConfig class represents the configuration for web security in the application.
 * It is responsible for configuring authentication and authorization mechanisms.
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	private static final String ADMIN = "ADMIN";
	/**
	 * The userDetailsService variable is an instance of the UserDetailsServiceImpl class.
	 * <p>
	 * UserDetailsServiceImpl is an implementation of Spring Security's UserDetailsService interface.
	 * It is responsible for retrieving user-related data.
	 */
	private final UserDetailsServiceImpl userDetailsService;
	/**
	 * This class implements the AuthenticationEntryPoint interface and is marked as a component.
	 * It is utilized to commence an authentication scheme and handle any exceptions during authentication process.
	 */
	private final AuthEntryPointJwt unauthorizedHandler;
	/**
	 * This class represents a utility class responsible for generating, parsing, and validating JSON Web Tokens (JWTs).
	 * It provides methods for generating a JWT token, extracting the username from a JWT token, and validating a JWT token.
	 */
	private final JwtUtils jwtUtils;

	/**
	 * This class represents the configuration for web security in the application.
	 * It is responsible for configuring authentication and authorization mechanisms.
	 */
	@Autowired
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler, JwtUtils jwtUtils) {
		this.userDetailsService = userDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.jwtUtils = jwtUtils;
	}

	/**
	 * Creates an instance of the AuthTokenFilter class with the provided JwtUtils and UserDetailsServiceImpl objects.
	 * The AuthTokenFilter class is responsible for authorizing the User by validating the JWT token.
	 *
	 * @param jwtUtils             The JwtUtils object that handles JWT token operations.
	 * @param userDetailsService  The UserDetailsServiceImpl object that handles User details services.
	 * @return An instance of AuthTokenFilter.
	 */
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
		return new AuthTokenFilter(jwtUtils, userDetailsService);
	}

	/**
	 * Creates and configures an instance of a DaoAuthenticationProvider.
	 * The authentication provider is responsible for authenticating users based on their credentials.
	 * It uses the user details service and password encoder defined in the containing class.
	 *
	 * @return The configured DaoAuthenticationProvider instance.
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	/**
	 * This method returns the AuthenticationManager from the provided AuthenticationConfiguration object.
	 *
	 * @param authConfig The AuthenticationConfiguration object that contains the AuthenticationManager.
	 * @return The AuthenticationManager obtained from the AuthenticationConfiguration object.
	 * @throws Exception If any error occurs during obtaining the AuthenticationManager.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/**
	 * Creates and configures an instance of a PasswordEncoder.
	 *
	 * @return a PasswordEncoder instance
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16);
	}

	/**
	 * Creates and configures a security filter chain for the HttpSecurity object.
	 *
	 * @param http the HttpSecurity object to configure the filter chain for
	 * @return the configured HttpSecurity object with the security filter chain applied
	 * @throws Exception if an error occurs during configuration
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
		// set the name of the attribute the CsrfToken will be populated on
		delegate.setCsrfRequestAttributeName("_csrf");
		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
		// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
		CsrfTokenRequestHandler requestHandler = delegate::handle;

		http.csrf(csrf -> csrf.csrfTokenRepository(tokenRepository).csrfTokenRequestHandler(requestHandler))
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// Authetification APIs
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/api/users/**").hasRole(ADMIN)
						//.requestMatchers(HttpMethod.PATCH,"/api/users/**").hasRole(ADMIN)
						// Role APIs
						.requestMatchers(HttpMethod.GET, "/api/role/films").hasAnyRole(ADMIN, "FILMS")
						.requestMatchers(HttpMethod.GET, "/api/role/people").hasAnyRole(ADMIN, "PEOPLE")
						.requestMatchers(HttpMethod.GET, "/api/role/locations").hasAnyRole(ADMIN, "LOCATIONS")
						.requestMatchers(HttpMethod.GET, "/api/role/species").hasAnyRole(ADMIN, "SPECIES")
						.requestMatchers(HttpMethod.GET, "/api/role/vehicles").hasAnyRole(ADMIN, "VEHICLES")
						// Get CSFR Token
						.requestMatchers(HttpMethod.GET, "/csrf").permitAll()
						// Swagger configuration
						.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/v3/**").permitAll()
						// Everything else need authorization
						.anyRequest()
						.authenticated());

		http.addFilterBefore(authenticationJwtTokenFilter(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class);

		http.authenticationProvider(authenticationProvider());

		return http.build();
	}

	/**
	 * Returns a new instance of RestTemplate.
	 *
	 * @return A RestTemplate object.
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}