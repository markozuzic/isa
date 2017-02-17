package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
		
	@ManyToOne
	@JoinColumn
	private Restaurant restaurant;
	
	public MenuItem() {
		
	}

	public MenuItem(String description, String name, double price, Restaurant restaurant) {
		super();
		this.description = description;
		this.name = name;
		this.price = price;
		this.restaurant = restaurant;
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}
