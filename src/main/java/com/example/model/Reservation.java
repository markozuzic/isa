package com.example.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Reservation {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private long restaurantId;
	
	@Column
	private Date dateTime;
	
	@Column 
	private int duration;
	
	@ManyToMany
	private List<TableRestaurant> tables;
	
	@Column(nullable = false)
	private long userId;
	
	

	public Reservation() {}
	
	public Reservation(long restaurantId, Date dateTime, int duration, long userId) {
		this.restaurantId = restaurantId;
		this.dateTime = dateTime;
		this.duration = duration;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<TableRestaurant> getTables() {
		return tables;
	}

	public void setTables(List<TableRestaurant> tables) {
		this.tables = tables;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getLength() {
		return duration;
	}

	public void setLength(int length) {
		this.duration = length;
	}
	
	//list tables
	
	
	
}
