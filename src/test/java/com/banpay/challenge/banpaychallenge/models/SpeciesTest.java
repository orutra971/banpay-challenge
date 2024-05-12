package com.banpay.challenge.banpaychallenge.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains a unit test for the Species class. It tests the getter methods
 * and asserts that they return the expected values. It also tests the equals, hashCode,
 * and toString methods of the Species class and asserts that two instances with the
 * same attribute values are considered equal, have the same hash code, and produce
 * the same string representation.
 */
class SpeciesTest {

	/**
	 * This method tests the Species class by creating two instances of the Species class
	 * and asserting that their getter methods return the expected values. It also tests
	 * the equals, hashCode, and toString methods of the Species class by asserting that
	 * two instances of the Species class with the same attribute values are considered equal,
	 * have the same hash code, and produce the same string representation.
	 */
	@Test
	void testSpecies() {
		Species species1 = new Species();

		species1.setId("1");
		species1.setName("Species Name");
		species1.setClassification("Classification Name");
		species1.setEye_color("Blue");
		species1.setHair_color("Black");
		species1.setPeople(Arrays.asList("Person1", "Person2"));
		species1.setFilms(Arrays.asList("Film1", "Film2"));
		species1.setUrl("http://example.com");

		Species species2 = new Species();

		species2.setId("1");
		species2.setName("Species Name");
		species2.setClassification("Classification Name");
		species2.setEye_color("Blue");
		species2.setHair_color("Black");
		species2.setPeople(Arrays.asList("Person1", "Person2"));
		species2.setFilms(Arrays.asList("Film1", "Film2"));
		species2.setUrl("http://example.com");

		// Test getters
		assertEquals("1", species1.getId());
		assertEquals("Species Name", species1.getName());
		assertEquals("Classification Name", species1.getClassification());
		assertEquals("Blue", species1.getEye_color());
		assertEquals("Black", species1.getHair_color());
		assertEquals("Person1", species1.getPeople().get(0));
		assertEquals("Person2", species1.getPeople().get(1));
		assertEquals("Film1", species1.getFilms().get(0));
		assertEquals("Film2", species1.getFilms().get(1));
		assertEquals("http://example.com", species1.getUrl());

		// Test equals, hashCode, and toString
		assertEquals(species1, species2);
		assertEquals(species1.hashCode(), species2.hashCode());
		assertEquals(species1.toString(), species2.toString());
	}

}