package com.banpay.challenge.banpaychallenge.security.services;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.Role;
import com.banpay.challenge.banpaychallenge.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The RoleService class provides operations related to roles in the system.
 * It utilizes Spring's @Service annotation for service-layer classes, which indicates that a class provides some sort
 * of business services. These classes tend to be thrown into the business layer.
 */
@Service
public class RoleService {

	/**
	 * RoleRepository instance for interacting with the database for Role data.
	 */
	private final RoleRepository roleRepository;


	/**
	 * Autowires RoleRepository.
	 *
	 * @param roleRepository RoleRepository to interact with the Roles database table.
	 */
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	/**
	 * Retrieves a Role by its ERole.
	 *
	 * @param role ERole of the Role to be retrieved.
	 * @return Role object.
	 * @throws RuntimeException when Role is not found in the repository.
	 */
	public Role getRole(ERole role) {
		return roleRepository.findByName(role)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	}

	/**
	 * Retrieves the role enum value based on the provided name.
	 *
	 * @param name the name of the role to retrieve. Must not be null.
	 * @return the role enum value associated with the provided name.
	 */
	public static ERole getRoleFromName(String name) {
		return switch (name) {
			case "user" -> ERole.ROLE_USER;
			case "admin" -> ERole.ROLE_ADMIN;
			case "films" -> ERole.ROLE_FILMS;
			case "people" -> ERole.ROLE_PEOPLE;
			case "locations" -> ERole.ROLE_LOCATIONS;
			case "species" -> ERole.ROLE_SPECIES;
			case "vehicles" -> ERole.ROLE_VEHICLES;
			default -> ERole.ROLE_USER;
		};
	}
}
