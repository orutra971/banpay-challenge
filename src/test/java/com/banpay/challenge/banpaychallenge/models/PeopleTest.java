package com.banpay.challenge.banpaychallenge.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the People class. It creates two People objects and sets their attributes.
 * Then it performs a series of assertions to test the getters of the objects.
 * It also tests the equals, hashCode, and toString methods of the People class.
 */
class PeopleTest {

	/**
	 * This method tests the People class.
	 * It creates two People objects and sets their attributes.
	 * Then it performs a series of assertions to test the getters of the objects.
	 * It also tests the equals, hashCode, and toString methods of the People class.
	 */
	@Test
	void testPeople() {
		People people1 = new People();

		people1.setId("1");
		people1.setName("Test Name");
		people1.setGender("Male");
		people1.setAge("30");
		people1.setEye_color("Brown");
		people1.setHair_color("Black");
		people1.setFilms(Arrays.asList("Film1", "Film2"));
		people1.setSpecies("Human");
		people1.setUrl("http://example.com");

		People people2 = new People();

		people2.setId("1");
		people2.setName("Test Name");
		people2.setGender("Male");
		people2.setAge("30");
		people2.setEye_color("Brown");
		people2.setHair_color("Black");
		people2.setFilms(Arrays.asList("Film1", "Film2"));
		people2.setSpecies("Human");
		people2.setUrl("http://example.com");

		// Test getters
		assertEquals("1", people1.getId());
		assertEquals("Test Name", people1.getName());
		assertEquals("Male", people1.getGender());
		assertEquals("30", people1.getAge());
		assertEquals("Brown", people1.getEye_color());
		assertEquals("Black", people1.getHair_color());
		assertEquals("Film1", people1.getFilms().get(0));
		assertEquals("Film2", people1.getFilms().get(1));
		assertEquals("Human", people1.getSpecies());
		assertEquals("http://example.com", people1.getUrl());

		// Test equals, hashCode and toString
		assertEquals(people1, people2);
		assertEquals(people1.hashCode(), people2.hashCode());
		assertEquals(people1.toString(), people2.toString());
	}

}