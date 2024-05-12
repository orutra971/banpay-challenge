package com.banpay.challenge.banpaychallenge.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The VehiclesTest class is responsible for testing the functionality of the Vehicles class.
 * It tests the setter and getter methods for the properties of a Vehicle object, as well as
 * the equality, hashCode, and toString methods.
 */
class VehiclesTest {

	/**
	 * Test method for vehicles.
	 *
	 * This method tests the functionality of the Vehicles class by creating two Vehicle objects,
	 * setting their properties, and then asserting the correctness of the properties using
	 * the getter methods. It also tests the equality of the two objects using the equals() method,
	 * the hashCode() method, and the toString() method.
	 */
	@Test
	void testVehicles() {
		Vehicles vehicle1 = new Vehicles();

		vehicle1.setId("1");
		vehicle1.setName("Vehicle 1");
		vehicle1.setDescription("Test vehicle 1");
		vehicle1.setVehicle_class("Class 1");
		vehicle1.setLength("123.45");
		vehicle1.setPilot("Pilot 1");
		vehicle1.setFilms(Arrays.asList("Film1", "Film2"));
		vehicle1.setUrl("http://example.com/url1");

		Vehicles vehicle2 = new Vehicles();

		vehicle2.setId("1");
		vehicle2.setName("Vehicle 1");
		vehicle2.setDescription("Test vehicle 1");
		vehicle2.setVehicle_class("Class 1");
		vehicle2.setLength("123.45");
		vehicle2.setPilot("Pilot 1");
		vehicle2.setFilms(Arrays.asList("Film1", "Film2"));
		vehicle2.setUrl("http://example.com/url1");

		// Test getters
		assertEquals("1", vehicle1.getId());
		assertEquals("Vehicle 1", vehicle1.getName());
		assertEquals("Test vehicle 1", vehicle1.getDescription());
		assertEquals("Class 1", vehicle1.getVehicle_class());
		assertEquals("123.45", vehicle1.getLength());
		assertEquals("Pilot 1", vehicle1.getPilot());
		assertEquals("Film1", vehicle1.getFilms().get(0));
		assertEquals("Film2", vehicle1.getFilms().get(1));
		assertEquals("http://example.com/url1", vehicle1.getUrl());

		// Test equals, hashCode and toString
		assertEquals(vehicle1, vehicle2);
		assertEquals(vehicle1.hashCode(), vehicle2.hashCode());
		assertEquals(vehicle1.toString(), vehicle2.toString());
	}

}