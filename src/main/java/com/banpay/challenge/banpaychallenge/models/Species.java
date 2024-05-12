package com.banpay.challenge.banpaychallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a Species in the Studio Ghibli API.
 * Each Species instance contains properties such as id, name, classification, eye color and hair color.
 * It also maintains a list of people belonging to the species, films featuring the species, and the species URL.
 *
 * Note: Any unknown properties received from API are ignored during the deserialization process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Species {

	private String id;
	private String name;
	private String classification;
	private String eye_color;
	private String hair_color;
	private List<String> people;
	private List<String> films;
	private String url;

	/**
	 * Default constructor for the Species class.
	 */
	public Species() {
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

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getEye_color() {
		return eye_color;
	}

	public void setEye_color(String eye_color) {
		this.eye_color = eye_color;
	}

	public String getHair_color() {
		return hair_color;
	}

	public void setHair_color(String hair_color) {
		this.hair_color = hair_color;
	}

	public List<String> getPeople() {
		return people;
	}

	public void setPeople(List<String> people) {
		this.people = people;
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
	 * Overrides the equals() method of the Object class to compare Species instances based on their field values.
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

		Species species = (Species) o;
		return  Objects.equals(id, species.id) &&
				Objects.equals(name, species.name) &&
				Objects.equals(classification, species.classification) &&
				Objects.equals(eye_color, species.eye_color) &&
				Objects.equals(hair_color, species.hair_color) &&
				Objects.equals(people, species.people) &&
				Objects.equals(films, species.films) &&
				Objects.equals(url, species.url);
	}

	/**
	 * Overrides the hashCode() method of the Object class.
	 * Provides a hash value for the Species instance, used in hashing-based collection classes.
	 *
	 * @return the hash value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, name, classification, eye_color, hair_color, people, films, url);
	}

	/**
	 * Overrides the toString() method of the Object class.
	 * Provides a string representation of the Species instance.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "Species{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", classification='" + classification + '\'' +
				", eye_color='" + eye_color + '\'' +
				", hair_color='" + hair_color + '\'' +
				", people=" + people +
				", films=" + films +
				", url='" + url + '\'' +
				'}';
	}
}
