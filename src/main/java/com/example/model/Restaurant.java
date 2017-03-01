package com.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String address;
	
	@Column
	private double longitude;
	
	@Column 
	private double latitude;
	
	@OneToMany
	private List<MenuItem> menu;
	
	@OneToMany
	private List<MenuItem> drinks;
	
	@Column 
	private double rating = 0;
	
	@Column 
	private int ratingCounter = 0;
	
	public Restaurant() {
		
	}

	public Restaurant(String name, String description) { //only used for testing
		super();
		this.name = name;
		this.description = description;
	}
	
	public Restaurant(String name, String description, String address) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
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

	public List<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItem> menu) {
		this.menu = menu;
	}

	public List<MenuItem> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<MenuItem> drinks) {
		this.drinks = drinks;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getRatingCounter() {
		return ratingCounter;
	}

	public void setRatingCounter(int ratingCounter) {
		this.ratingCounter = ratingCounter;
	}
	
}
