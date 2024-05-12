package com.banpay.challenge.banpaychallenge.repository;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The RoleRepository interface extends the JpaRepository interface from Spring Data JPA to provide persistence layer functionalities.
 * Specifically, it provides operations related to the Role entity model, including CRUD operations and a custom operation to find a Role by its name.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * This method finds a Role based on its name.
	 * It returns an Optional object containing a Role.
	 * If no Role is found with the given name, it returns an empty Optional.
	 *
	 * @param name The name of the Role as an ERole Enum.
	 * @return An Optional object containing a Role entity (if found).
	 */
	Optional<Role> findByName(ERole name);
}
