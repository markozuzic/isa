package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MenuItem {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private double price;
	
	@Column
	private double rating = 0;
	
	@Column
	private int ratingCounter = 0;
	
	@Column
	private String type;

	
	public MenuItem() {
		
	}

	public MenuItem(String description, String name, double price, String type) {
		super();
		this.description = description;
		this.name = name;
		this.price = price;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
		
}
