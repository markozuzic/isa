package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Visit {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Reservation reservation;
	
	@Column
	private String restaurantName;
	
	@Column
	private boolean ratedRestaurant = false;
	
	@Column
	private boolean ratedService = false;
	
	@Column
	private boolean ratedMeal = false;

	public Visit(){}

	public Visit(User user, Reservation reservation, String restaurantName) {
		super();
		this.user = user;
		this.reservation = reservation;
		this.restaurantName = restaurantName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public boolean getRatedRestaurant() {
		return ratedRestaurant;
	}

	public void setRatedRestaurant(boolean ratedRestaurant) {
		this.ratedRestaurant = ratedRestaurant;
	}

	public boolean getRatedService() {
		return ratedService;
	}

	public void setRatedService(boolean ratedService) {
		this.ratedService = ratedService;
	}

	public boolean getRatedMeal() {
		return ratedMeal;
	}

	public void setRatedMeal(boolean ratedMeal) {
		this.ratedMeal = ratedMeal;
	}

	
	
}
