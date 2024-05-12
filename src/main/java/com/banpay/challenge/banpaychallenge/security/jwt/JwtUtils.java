package com.banpay.challenge.banpaychallenge.security.jwt;

import java.util.Date;

import com.banpay.challenge.banpaychallenge.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * JwtUtils is a utility class responsible for generating, parsing and validating JSON Web Tokens.
 * It carries out the process of encoding and decoding the JWTs used in the system for user authentication.
 */
@Component
public class JwtUtils {

	/**
	 * Logger for logging messages in the JwtUtils class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	/**
	 * Secret key for JWT generation and validation.
	 */
	@Value("${banpay-challenge.app.jwtSecret}")
	private String jwtSecret;

	/**
	 * Time period of JWT token's validity.
	 */
	@Value("${banpay-challenge.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	/**
	 * Generates a JWT Token using the passed Authentication object.
	 *
	 * @param authentication Spring Authentication object from which to extract user details and generate token
	 * @return JWT Token as string
	 */
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.subject((userPrincipal.getUsername()))
				.issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key(), Jwts.SIG.HS256)
				.compact();
	}

	/**
	 * Creates a SecretKey to be used in JWT token generation and validation.
	 *
	 * @return SecretKey for JWT
	 */
	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	/**
	 * Extracts the username from the JWT token.
	 *
	 * @param token JWT token
	 * @return Extracted username as string.
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	/**
	 * Validates the JWT token
	 *
	 * @param authToken JWT token to be validated
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().verifyWith(key()).build().parse(authToken).getPayload();
			return true;
		}
		catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}
		catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		}
		catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}
		catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
