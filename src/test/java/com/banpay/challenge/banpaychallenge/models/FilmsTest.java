package com.banpay.challenge.banpaychallenge.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains a JUnit test for the Films class.
 * It tests the methods of the Films class, including setting properties, asserting values, and testing the equals() and hashCode() methods.
 */
class FilmsTest {

	/**
	 * Tests the Films class by creating a film object, setting its properties,
	 * and asserting that the values are correct.
	 * It also tests the equals() and hashCode() methods of the Films class.
	 */
	@Test
	void testFilms() {
		Films film = new Films();

		film.setId("1");
		film.setTitle("Test Title");
		film.setOriginal_title("Test Original Title");
		film.setOriginal_title_romanised("Test Original Title Romanised");
		film.setDescription("Test Description");
		film.setDirector("Test Director");
		film.setProducer("Test Producer");
		film.setRelease_date("2024");
		film.setRunning_time("120");
		film.setRt_score("80");
		film.setPeople(Arrays.asList("Person1", "Person2"));
		film.setSpecies(Arrays.asList("Species1", "Species2"));
		film.setLocations(Arrays.asList("Location1", "Location2"));
		film.setVehicles(Arrays.asList("Vehicle1", "Vehicle2"));
		film.setUrl("https://example.com");

		// Test getters
		assertEquals("1", film.getId());
		assertEquals("Test Title", film.getTitle());
		assertEquals("Test Original Title", film.getOriginal_title());
		assertEquals("Test Description", film.getDescription());
		assertEquals("Test Director", film.getDirector());
		assertEquals("Test Producer", film.getProducer());
		assertEquals("2024", film.getRelease_date());
		assertEquals("120", film.getRunning_time());
		assertEquals("80", film.getRt_score());
		assertEquals("Person1", film.getPeople().get(0));
		assertEquals("Person2", film.getPeople().get(1));
		assertEquals("Species1", film.getSpecies().get(0));
		assertEquals("Species2", film.getSpecies().get(1));
		assertEquals("Location1", film.getLocations().get(0));
		assertEquals("Location2", film.getLocations().get(1));
		assertEquals("Vehicle1", film.getVehicles().get(0));
		assertEquals("Vehicle2", film.getVehicles().get(1));
		assertEquals("https://example.com", film.getUrl());

		Films film2 = new Films();
		film2.setId("1");
		film2.setTitle("Test Title");
		film2.setOriginal_title("Test Original Title");
		film2.setOriginal_title_romanised("Test Original Title Romanised");
		film2.setDescription("Test Description");
		film2.setDirector("Test Director");
		film2.setProducer("Test Producer");
		film2.setRelease_date("2024");
		film2.setRunning_time("120");
		film2.setRt_score("80");
		film2.setPeople(Arrays.asList("Person1", "Person2"));
		film2.setSpecies(Arrays.asList("Species1", "Species2"));
		film2.setLocations(Arrays.asList("Location1", "Location2"));
		film2.setVehicles(Arrays.asList("Vehicle1", "Vehicle2"));
		film2.setUrl("https://example.com");

		// Testing equals and hashCode and toString
		assertTrue(film.equals(film2) && film2.equals(film));
		assertEquals(film.hashCode(), film2.hashCode());
		assertEquals(film.toString(), film2.toString());
	}
}