package com.banpay.challenge.banpaychallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a Vehicle in the Studio Ghibli API.
 * Each Vehicle instance contains properties such as id, name, description, vehicle class, length, pilot, films, and URL.
 *
 * Note: Any unknown properties received from API are ignored during the deserialization process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicles {

	private String id;
	private String name;
	private String description;
	private String vehicle_class;
	private String length;
	private String pilot;
	private List<String> films;
	private String url;

	/**
	 * Default constructor for the Vehicles class.
	 */
	public Vehicles() {
		//
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVehicle_class() {
		return vehicle_class;
	}

	public void setVehicle_class(String vehicle_class) {
		this.vehicle_class = vehicle_class;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPilot() {
		return pilot;
	}

	public void setPilot(String pilot) {
		this.pilot = pilot;
	}

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Overrides the equals() method of the Object class to compare Vehicles instances based on their field values.
	 *
	 * @param o The Object to compare with the current instance
	 * @return true if the Objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Vehicles vehicles = (Vehicles) o;
		return Objects.equals(id, vehicles.id) &&
				Objects.equals(name, vehicles.name) &&
				Objects.equals(description, vehicles.description) &&
				Objects.equals(vehicle_class, vehicles.vehicle_class) &&
				Objects.equals(length, vehicles.length) &&
				Objects.equals(pilot, vehicles.pilot) &&
				Objects.equals(films, vehicles.films) &&
				Objects.equals(url, vehicles.url);
	}

	/**
	 * Overrides the hashCode() method of the Object class.
	 * Provides a hash value for the Vehicles instance, used in hashing-based collection classes.
	 *
	 * @return the hash value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, vehicle_class, length, pilot, films, url);
	}

	/**
	 * Overrides the toString() method of the Object class.
	 * Provides a string representation of the Vehicles instance.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "Vehicles{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", vehicle_class='" + vehicle_class + '\'' +
				", length='" + length + '\'' +
				", pilot='" + pilot + '\'' +
				", films=" + films +
				", url='" + url + '\'' +
				'}';
	}
}
