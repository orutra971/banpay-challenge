package com.banpay.challenge.banpaychallenge.security.jwt;

import com.banpay.challenge.banpaychallenge.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The AuthTokenFilter class extends OncePerRequestFilter from the Spring web filter module.
 * It is used as a filter that is executed once per request within a single request thread.
 * This class authorizes the User by validating the JWT token.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

	/**
	 * Instance of Logger to log details.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

	/**
	 * <p>
	 * The AUTHORIZED_ENDPOINTS variable is a list of strings that contains the authorized endpoints in the system.
	 * These are the endpoints that do not require authentication and can be accessed by any user.
	 *</p>
	 * <p>
	 * This variable is used within the AuthTokenFilter class to validate the incoming requests and check if they
	 * are authorized or not. Only the endpoints specified in this list are allowed to be accessed without
	 * authentication.
	 * </p>
	 */
	private static final List<String> AUTHORIZED_ENDPOINTS = Arrays.asList("/csrf",
																		   "/api/auth/signup",
																		   "/api/auth/signin",
																		   "/swagger-ui.html",
																		   "/swagger-ui/**",
																		   "/v3/**");

	/**
	 * JwtUtils to handle JWT token operations.
	 */
	private final JwtUtils jwtUtils;
	/**
	 * UserDetailsServiceImpl to handle User details services.
	 */
	private final UserDetailsServiceImpl userDetailsService;

	/**
	 * Autowires JwtUtils and UserDetailsServiceImpl
	 *
	 * @param jwtUtils handles JWT token operations
	 * @param userDetailsService handles User details services
	 */
	@Autowired
	public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * Implementation of the filter method for each request to check the validity of the JWT Token.
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param filterChain FilterChain
	 * @throws ServletException if a Servlet specific exception occurs
	 * @throws IOException in case of input/output error
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestURI = request.getRequestURI();

		if (AUTHORIZED_ENDPOINTS.contains(requestURI)) {
			filterChain.doFilter(request, response);
			return;
    	}

		try {
			String jwt = parseJwt(request);
			if (jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			LOGGER.error("Cannot set user authentication: {}", e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Parses JWT from the authorization header
	 *
	 * @param request HttpServletRequest
	 * @return token parsed from the header
	 */
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");


		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return headerAuth;
	}
}
