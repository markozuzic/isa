package com.example.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private double length;
	
	public Reservation() {}
	
	public Reservation(long restaurantId, Date dateTime, double length) {
		this.restaurantId = restaurantId;
		this.dateTime = dateTime;
		this.length = length;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	//list tables
	
	
	
}
