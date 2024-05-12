package com.banpay.challenge.banpaychallenge.security.services;

import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The UserDetailsServiceImpl class is an implementation of spring security's UserDetailsService interface.
 * It is used for retrieving user-related data.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * UserRepository instance for interacting with User data in the database.
	 */
	private final UserRepository userRepository;

	/**
	 * Autowires the UserRepository.
	 *
	 * @param userRepository repository to handle user data.
	 */
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * This is an overridden method from UserDetailsService interface, loads a user's data
	 * by its username and builds a UserDetails object using this data.
	 *
	 * @param username the username identifying the user whose data you want to load
	 * @return a fully populated UserDetails object (never null)
	 * @throws UsernameNotFoundException if the user could not be found or if
	 *                                   the user doesn't have any authorities
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

}
