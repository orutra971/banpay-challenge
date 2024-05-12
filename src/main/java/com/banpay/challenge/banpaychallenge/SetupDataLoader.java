package com.banpay.challenge.banpaychallenge;

import com.banpay.challenge.banpaychallenge.models.ERole;
import com.banpay.challenge.banpaychallenge.models.Role;
import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.repository.RoleRepository;
import com.banpay.challenge.banpaychallenge.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SetupDataLoader class is a component which implements ApplicationListener interface.
 * It is designed to be used at application startup - it populates the database with necessary roles and an admin user.
 */
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SetupDataLoader.class);

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	boolean alreadySetup = false;

	/**
	 * Constructor for the SetupDataLoader class
	 *
	 * @param userRepository  UserRepository instance
	 * @param roleRepository  RoleRepository instance
	 * @param passwordEncoder PasswordEncoder instance
	 */
	@Autowired
	public SetupDataLoader(UserRepository userRepository,
						   RoleRepository roleRepository,
						   PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * This method is triggered on application start to create roles on the database
	 *
	 * @param event ContextRefreshedEvent instance
	 */
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("onApplicationEvent: {} ", alreadySetup);
		if (alreadySetup) {
			return;
		}
		createRoleIfNotFound(ERole.ROLE_ADMIN);
		createRoleIfNotFound(ERole.ROLE_USER);
		createRoleIfNotFound(ERole.ROLE_FILMS);
		createRoleIfNotFound(ERole.ROLE_PEOPLE);
		createRoleIfNotFound(ERole.ROLE_LOCATIONS);
		createRoleIfNotFound(ERole.ROLE_SPECIES);
		createRoleIfNotFound(ERole.ROLE_VEHICLES);

		Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
		if (userRole.isEmpty()) {
			return;
		}

		User user = getJson("classpath:admin.json");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(user.getRoles().stream().map(u -> {
					Optional<Role> role = roleRepository.findByName(u.getName());
					return role.orElseGet(userRole::get);
				}).collect(Collectors.toSet())
		);

		userRepository.save(user);

		alreadySetup = true;
	}

	/**
	 * Creates role if it's not found in database
	 *
	 * @param name Name of the role
	 * @return saved Role instance
	 */
	@Transactional
	public Role createRoleIfNotFound(ERole name) {
		Optional<Role> role = roleRepository.findByName(name);
		if (role.isEmpty()) {
			Role saveRole = new Role(name);
			return roleRepository.save(saveRole);
		}
		return role.get();
	}

	/**
	 * Reads user data from JSON file
	 *
	 * @param path Path to the JSON file
	 * @return User instance with data from JSON file
	 */
	public User getJson(String path) {
		LOGGER.info("getJson: {} ", alreadySetup);
		File file = null;
		try {
			file = ResourceUtils.getFile(path);
			//Read File Content
			String content = new String(Files.readAllBytes(file.toPath()));
			//Get a Json String
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValueAsString(ERole.ROLE_ADMIN);
			return objectMapper.readValue(content, User.class);
		}
		catch (FileNotFoundException e) {
			LOGGER.error("FileNotFoundException: {} ", e.getMessage());
		}
		catch (IOException e) {
			LOGGER.error("IOException: {} ", e.getMessage());
		}
		return null;
	}

}