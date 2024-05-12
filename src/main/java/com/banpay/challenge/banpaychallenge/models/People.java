package com.banpay.challenge.banpaychallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a Person in the Studio Ghibli API.
 * Each Person instance contains properties such as id, name, gender, age, eye color, and hair color.
 * It maintains a list of films the person appears in, species details, and the person's URL.
 *
 * Note: Any unknown properties received from API are ignored during the deserialization process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class People {

	private String id;
	private String name;
	private String gender;
	private String age;
	private String eye_color;
	private String hair_color;
	private List<String> films;
	private String species;
	private String url;

	public People() {
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Overrides the equals() method of the Object class to compare People instances based on their field values.
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
		People people = (People) o;
		return 	Objects.equals(id, people.id) &&
				Objects.equals(name, people.name) &&
				Objects.equals(gender, people.gender) &&
				Objects.equals(age, people.age) &&
				Objects.equals(eye_color, people.eye_color) &&
				Objects.equals(hair_color, people.hair_color) &&
				Objects.equals(films, people.films) &&
				Objects.equals(species, people.species) &&
				Objects.equals(url, people.url);
	}

	/**
	 * Overrides the hashCode() method of the Object class.
	 * Provides a hash value for the People instance, used in hashing-based collection classes.
	 *
	 * @return the hash value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, name, gender, age, eye_color, hair_color, films, species, url);
	}

	/**
	 * Overrides the toString() method of the Object class.
	 * Provides a string representation of the People instance.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "People{" + "id='" + id + '\'' +
				", name='" + name + '\'' +
				", gender='" + gender + '\'' +
				", age='" + age + '\'' +
				", eye_color='" + eye_color +'\'' +
				", hair_color='" + hair_color + '\'' +
				", films=" + films +
				", species='" + species + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
