package com.banpay.challenge.banpaychallenge.security.services;

import com.banpay.challenge.banpaychallenge.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The UserDetailsImpl class is the implementation of spring security's UserDetails interface
 * to provide core user information to the framework.
 */
public class UserDetailsImpl implements UserDetails {

	@Serial
	private static final long serialVersionUID = 1324565465634621L;

	/**
	 * User's ID
	 */
	private final Long id;

	/**
	 * Username of the user.
	 */
	private final String username;

	/**
	 * Email of the user.
	 */
	private final String email;

	/**
	 * Password of the user.
	 */
	@JsonIgnore
	private final String password;

	/**
	 * Granted authorities of the user.
	 */
	private final Collection<? extends GrantedAuthority> authorities;

	/**
	 * A constructor that creates a UserDetailsImpl object with specified parameters.
	 */
	public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	/**
	 * This method constructs a UserDetailsImpl object from User object.
	 */
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
	}

	/**
	 * Method to get a collection of all authorities granted to the user.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long id() {
		return id;
	}

	/**
	 * Method to get email of the user.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to get the password of the user.
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Method to get the username of the user.
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * Method to check if the user account has expired.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Method to check if the user account is locked.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Method to check if the user's credentials has expired.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Method to check if the user is enabled.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Overrides equals() method of Object class to check equality of UserDetailsImpl objects.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	/**
	 * Overrides hashcode() method of Object class to generate hash for UserDetailsImpl object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, password, authorities);
	}
}
