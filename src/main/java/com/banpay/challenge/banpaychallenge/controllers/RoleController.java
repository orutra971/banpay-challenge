package com.banpay.challenge.banpaychallenge.controllers;


import com.banpay.challenge.banpaychallenge.models.*;
import com.banpay.challenge.banpaychallenge.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * This class provides the rest controller for the role API.
 * The API roots the endpoint to "/api/role".
 * An instance of RestTemplate exists in each controller instance.
 * This template is used to forward requests to the Studio Ghibli API
 * which is specified by {@link #studioGhibliUrl}.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

	/**
	 * Logger instance for logging events, info, and errors.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	/**
	 * The restTemplate variable is an instance of the RestTemplate class.
	 * <p>
	 * You can use RestTemplate to interact with the Studio Ghibli API and retrieve data from it.
	 * For example, you can use RestTemplate to send GET requests to endpoints like "/films", "/people", etc. and receive the corresponding data.
	 */
	private final RestTemplate restTemplate;
	// The URL of the Studio Ghibli API
	@Value("${studio-ghibli.api.url}")
	private String studioGhibliUrl;

	/**
	 * Represents a controller for managing roles.
	 */
	@Autowired
	public RoleController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/**
	 * Provides an endpoint for getting all the films.
	 * Returns a response entity with a message and a body containing a list of films.
	 * Utilizes an exchange operation to the "/films" endpoint of the Studio Ghibli API.
	 *
	 * @return ResponseEntity with the message "All films obtained" and the body containing the list of films
	 */
	@GetMapping("/films")
	public ResponseEntity<MessageResponse<List<Films>>> getAllFilms() {
		LOGGER.info("Getting all films");
		ResponseEntity<List<Films>> films = restTemplate
				.exchange(studioGhibliUrl + "/films",
					 HttpMethod.GET,
					 null,
					 new ParameterizedTypeReference<>() {
					 });

		return ResponseEntity.ok(new MessageResponse<>("All films obtained", films.getBody()));
	}

	/**
	 * Provides an endpoint for getting all the people.
	 * Returns a response entity with a message and a body containing a list of people.
	 * Utilizes an exchange operation to the "/people" endpoint of the Studio Ghibli API.
	 *
	 * @return ResponseEntity with the message "All people obtained" and the body containing the list of people
	 */
	@GetMapping("/people")
	public ResponseEntity<MessageResponse<List<People>>> getAllPeople() {
		LOGGER.info("Getting all people");
		ResponseEntity<List<People>> people = restTemplate
				.exchange(studioGhibliUrl + "/people",
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<>() {
						  });

		return ResponseEntity.ok(new MessageResponse<>("All people obtained", people.getBody()));
	}

	/**
	 * Provides an endpoint for getting all the locations.
	 * Returns a response entity with a message and a body containing a list of people.
	 * Utilizes an exchange operation to the "/locations" endpoint of the Studio Ghibli API.
	 *
	 * @return ResponseEntity with the message "All locations obtained" and the body containing the list of locations
	 */
	@GetMapping("/locations")
	public ResponseEntity<MessageResponse<List<Locations>>> getAllLocations() {
		LOGGER.info("Getting all locations");
		ResponseEntity<List<Locations>> locations = restTemplate
				.exchange(studioGhibliUrl + "/locations",
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<>() {
						  });

		return ResponseEntity.ok(new MessageResponse<>("All locations obtained", locations.getBody()));
	}

	/**
	 * Provides an endpoint for getting all the species.
	 * Returns a response entity with a message and a body containing a list of people.
	 * Utilizes an exchange operation to the "/species" endpoint of the Studio Ghibli API.
	 *
	 * @return ResponseEntity with the message "All species obtained" and the body containing the list of species
	 */
	@GetMapping("/species")
	public ResponseEntity<MessageResponse<List<Species>>> getAllSpecies() {
		LOGGER.info("Getting all species");
		ResponseEntity<List<Species>> species = restTemplate
				.exchange(studioGhibliUrl + "/species",
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<>() {
						  });

		return ResponseEntity.ok(new MessageResponse<>("All species obtained", species.getBody()));
	}

	/**
	 * Provides an endpoint for getting all the vehicles.
	 * Returns a response entity with a message and a body containing a list of people.
	 * Utilizes an exchange operation to the "/vehicles" endpoint of the Studio Ghibli API.
	 *
	 * @return ResponseEntity with the message "All vehicles obtained" and the body containing the list of vehicles
	 */
	@GetMapping("/vehicles")
	public ResponseEntity<MessageResponse<List<Vehicles>>> getAllVehicles() {
		LOGGER.info("Getting all vehicles");
		ResponseEntity<List<Vehicles>> vehicles = restTemplate
				.exchange(studioGhibliUrl + "/vehicles",
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<>() {
						  });

		return ResponseEntity.ok(new MessageResponse<>("All vehicles obtained", vehicles.getBody()));
	}

}
