package com.banpay.challenge.banpaychallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a Location in the Studio Ghibli API.
 * Each Location instance contains properties such as id, name, climate, and terrain.
 * It also contains the amount of surface water, a list of residents, associated films and the location's URL.
 *
 * Note: Any unknown properties received from API are ignored during the deserialization process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Locations {

	private String id;
	private String name;
	private String climate;
	private String terrain;
	private String surface_water;
	private List<String> residents;
	private List<String> films;
	private String url;

	/**
	 * Default constructor for the Locations class.
	 */
	public Locations() {
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

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public String getSurface_water() {
		return surface_water;
	}

	public void setSurface_water(String surface_water) {
		this.surface_water = surface_water;
	}

	public List<String> getResidents() {
		return residents;
	}

	public void setResidents(List<String> residents) {
		this.residents = residents;
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
	 * Overrides the equals() method of the Object class to compare Location instances based on their field values.
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
		Locations locations = (Locations) o;
		return 	Objects.equals(id, locations.id) &&
				Objects.equals(name, locations.name) &&
				Objects.equals(climate, locations.climate) &&
				Objects.equals(terrain, locations.terrain) &&
				Objects.equals(surface_water, locations.surface_water) &&
				Objects.equals(residents, locations.residents) &&
				Objects.equals(films, locations.films) &&
				Objects.equals(url, locations.url);
	}

	/**
	 * Overrides the hashCode() method of the Object class.
	 * Provides a hash value for the Location instance, used in hashing-based collection classes.
	 *
	 * @return the hash value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, name, climate, terrain, surface_water, residents, films, url);
	}

	/**
	 * Overrides the toString() method of the Object class.
	 * Provides a string representation of the Location instance.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "Locations{" + "id='" + id + '\'' +
				", name='" + name + '\'' +
				", climate='" + climate + '\'' +
				", terrain='" + terrain + '\'' +
				", surface_water='" + surface_water + '\'' +
				", residents='" + residents + '\'' +
				", films='" + films + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
