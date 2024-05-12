package com.banpay.challenge.banpaychallenge.repository;

import com.banpay.challenge.banpaychallenge.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository is an interface that carries out various operations related to User entity.
 * It extends JpaRepository to handle database operations for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * This method is used to find a user using the username.
	 *
	 * @param username User's username
	 * @return an Optional of User
	 */
	Optional<User> findByUsername(String username);

	/**
	 * This method is used to check whether a user exists in the database by the username.
	 *
	 * @param username User's username
	 * @return a boolean indicating the user's existence
	 */
	boolean existsByUsername(String username);

	/**
	 * This method is used to check whether a user exists in the database by the email.
	 *
	 * @param email User's email
	 * @return a boolean indicating the user's existence
	 */
	boolean existsByEmail(String email);
}
