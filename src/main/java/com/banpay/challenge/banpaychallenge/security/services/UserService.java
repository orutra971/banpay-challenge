package com.banpay.challenge.banpaychallenge.security.services;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.Role;
import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * UserService is a service class that provides functionalities for managing users.
 */
@Service
public class UserService {

	/**
	 * UserRepository instance for interacting with User data in the database.
	 */
	private final UserRepository userRepository;

	/**
	 * RoleService instance for managing user roles.
	 */
	private final RoleService roleService;

	/**
	 * Autowires the UserRepository and RoleService.
	 */
	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	/**
	 * Creates a new user.
	 */
	public User createUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Saves and returns the updated user data.
	 */
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Returns all users.
	 */
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Returns user data corresponding to the given id.
	 */
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	/**
	 * Returns user data corresponding to the given username.
	 */
	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Checks and returns whether the user with the given username exists.
	 */
	public boolean existsUserByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	/**
	 * Checks and returns whether the user with the given email exists.
	 */
	public boolean existsUserByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	/**
	 * Delete the user corresponding to the given id.
	 */
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	/**
	 * Delete all users.
	 */
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	/**
	 * Assign roles to the given user. Additional roles can be assigned
	 * if the administrator flag is set.
	 */
	public void assignRolesToUser(User user, Set<String> strRoles, boolean byAdmin) {
		Set<Role> roles = new HashSet<>();

		if (byAdmin && strRoles.contains("admin")) {
			roles.add(roleService.getRole(ERole.ROLE_ADMIN));
		}
		else {
			roles.add(roleService.getRole(ERole.ROLE_USER));
		}

		strRoles.forEach(role -> {
			switch (role) {
				case "films":
					Role filmRole = roleService.getRole(ERole.ROLE_FILMS);
					roles.add(filmRole);
					break;
				case "people":
					Role peopleRole = roleService.getRole(ERole.ROLE_PEOPLE);
					roles.add(peopleRole);
					break;
				case "locations":
					Role locationRole = roleService.getRole(ERole.ROLE_LOCATIONS);
					roles.add(locationRole);
					break;
				case "species":
					Role speciesRole = roleService.getRole(ERole.ROLE_SPECIES);
					roles.add(speciesRole);
					break;
				case "vehicles":
					Role vehiclesRole = roleService.getRole(ERole.ROLE_VEHICLES);
					roles.add(vehiclesRole);
					break;
				default:
					break;
			}
		});

		user.setRoles(roles);
	}
}
