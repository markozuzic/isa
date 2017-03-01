package com.example.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Visit {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private long userId;
	
	@Column(nullable = false)
	private long restaurantId;
	
	@Column(nullable = false)
	private String restaurantName;
	
	@Column
	private Date date;
	
	@ManyToMany
	private List<MenuItem> menuItems;

	public Visit(){}

	public Visit(long user, long restaurant, String restaurantName, Date date) {
		super();
		this.userId = user;
		this.restaurantId = restaurant;
		this.restaurantName = restaurantName;
		this.date = date;
	}

	public long getUser() {
		return userId;
	}

	public void setUser(long user) {
		this.userId = user;
	}

	public long getRestaurant() {
		return restaurantId;
	}

	public void setRestaurant(long restaurant) {
		this.restaurantId = restaurant;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
}
