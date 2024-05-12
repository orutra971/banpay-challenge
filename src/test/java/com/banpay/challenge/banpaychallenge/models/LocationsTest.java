package com.banpay.challenge.banpaychallenge.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The LocationsTest class is used to test the functionality of the Location class in the Studio Ghibli API.
 */
class LocationsTest {

	/**
	 * This method is used to test the functionality of the Location class in the Studio Ghibli API.
	 * It creates two Location instances and assigns values to their properties.
	 * Then, it performs tests on the getter methods to verify if the values are correctly set.
	 * Finally, it tests the equals(), hashCode(), and toString() methods to verify if they provide the expected results.
	 */
	@Test
	void testLocation() {
		Locations location1 = new Locations();

		location1.setId("1");
		location1.setName("Location Name");
		location1.setClimate("Temperate");
		location1.setTerrain("Mountain");
		location1.setSurface_water("20");
		location1.setResidents(Arrays.asList("Resident1", "Resident2"));
		location1.setFilms(Arrays.asList("Film1", "Film2"));
		location1.setUrl("http://example.com");

		Locations location2 = new Locations();

		location2.setId("1");
		location2.setName("Location Name");
		location2.setClimate("Temperate");
		location2.setTerrain("Mountain");
		location2.setSurface_water("20");
		location2.setResidents(Arrays.asList("Resident1", "Resident2"));
		location2.setFilms(Arrays.asList("Film1", "Film2"));
		location2.setUrl("http://example.com");

		// Test getters
		assertEquals("1", location1.getId());
		assertEquals("Location Name", location1.getName());
		assertEquals("Temperate", location1.getClimate());
		assertEquals("Mountain", location1.getTerrain());
		assertEquals("20", location1.getSurface_water());
		assertEquals("Resident1", location1.getResidents().get(0));
		assertEquals("Resident2", location1.getResidents().get(1));
		assertEquals("Film1", location1.getFilms().get(0));
		assertEquals("Film2", location1.getFilms().get(1));
		assertEquals("http://example.com", location1.getUrl());

		// Test equals, hashCode and toString
		assertEquals(location1, location2);
		assertEquals(location1.hashCode(), location2.hashCode());
		assertEquals(location1.toString(), location2.toString());
	}

}