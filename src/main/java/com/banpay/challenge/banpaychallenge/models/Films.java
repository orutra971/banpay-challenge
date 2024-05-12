package com.banpay.challenge.banpaychallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a Film in the Studio Ghibli API.
 * Each Film instance contains properties such as id, title, description, director, and producer.
 * Lists of Strings representing associated people, species, locations, and vehicles are also present.
 * The Film's URL in the Studio Ghibli API is stored in the url property.
 *
 * Note: Any unknown properties received from the API are ignored during the deserialization process.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Films {

	private String id;
	private String title;
	private String original_title;
	private String original_title_romanised;
	private String description;
	private String director;
	private String producer;
	private String release_date;
	private String running_time;
	private String rt_score;
	private List<String> people;
	private List<String> species;
	private List<String> locations;
	private List<String> vehicles;
	private String url;

	/**
	 * Default constructor for the Films class.
	 */
	public Films() {
		//
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOriginal_title_romanised() {
		return original_title_romanised;
	}

	public void setOriginal_title_romanised(String original_title_romanised) {
		this.original_title_romanised = original_title_romanised;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getRunning_time() {
		return running_time;
	}

	public void setRunning_time(String running_time) {
		this.running_time = running_time;
	}

	public String getRt_score() {
		return rt_score;
	}

	public void setRt_score(String rt_score) {
		this.rt_score = rt_score;
	}

	public List<String> getPeople() {
		return people;
	}

	public void setPeople(List<String> people) {
		this.people = people;
	}

	public List<String> getSpecies() {
		return species;
	}

	public void setSpecies(List<String> species) {
		this.species = species;
	}

	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public List<String> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<String> vehicles) {
		this.vehicles = vehicles;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Overrides the equals() method of the Object class to compare Film instances based on their field values.
	 *
	 * @param o The object to compare with the current instance
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Films films = (Films) o;
		return Objects.equals(id, films.id) &&
				Objects.equals(title, films.title) &&
				Objects.equals(original_title, films.original_title) &&
				Objects.equals(original_title_romanised, films.original_title_romanised) &&
				Objects.equals(description, films.description) &&
				Objects.equals(director, films.director) &&
				Objects.equals(producer, films.producer) &&
				Objects.equals(release_date, films.release_date) &&
				Objects.equals(running_time, films.running_time) &&
				Objects.equals(rt_score, films.rt_score) &&
				Objects.equals(people, films.people) &&
				Objects.equals(species, films.species) &&
				Objects.equals(locations, films.locations) &&
				Objects.equals(vehicles, films.vehicles) &&
				Objects.equals(url, films.url);
	}

	/**
	 * Overrides the hashCode() method of the Object class.
	 * Provides a hash value for the Film instance, which is used in hashing-based collection classes.
	 *
	 * @return the hash value
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id,
							title,
							original_title,
							original_title_romanised,
							description,
							director,
							producer,
							release_date,
							running_time,
							rt_score,
							people,
							species,
							locations,
							vehicles,
							url);
	}

	/**
	 * Overrides the toString() method of the Object class.
	 * Provides a string representation of the Film instance.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "Films{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", original_title='" + original_title + '\'' +
				", original_title_romanised='" + original_title_romanised + '\'' +
				", description='" + description + '\'' +
				", director='" + director + '\'' +
				", producer='" + producer + '\'' +
				", release_date='" + release_date + '\'' +
				", running_time='" + running_time + '\'' +
				", rt_score='" + rt_score + '\'' +
				", people=" + people +
				", species=" + species +
				", locations=" + locations +
				", vehicles=" + vehicles +
				", url='" + url + '\'' +
				'}';
	}
}
