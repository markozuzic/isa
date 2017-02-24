package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TableRestaurant {
	@Id
	private int tableNumber;
	
	@Column(nullable = false)
	private String segment;
	
	@Column(nullable = false)
	private long restaurant;
	
	@Column(nullable = false)
	private int numberOfChairs;
	
	@Column(nullable = false)
	private int x;
	
	@Column(nullable = false)
	private int y;
	
	@Column
	private boolean isAvailable;
	
	public TableRestaurant() {
		
	}
	
	public TableRestaurant(int tableNumber, String segment, long restaurant, int numberOfChairs,
						   int x, int y) {
		this.tableNumber = tableNumber;
		this.segment = segment;
		this.restaurant = restaurant;
		this.numberOfChairs = numberOfChairs;
		this.x = x;
		this.y = y;
		this.isAvailable = true;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public long getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(long restaurant) {
		this.restaurant = restaurant;
	}

	public int getNumberOfChairs() {
		return numberOfChairs;
	}

	public void setNumberOfChairs(int numberOfChairs) {
		this.numberOfChairs = numberOfChairs;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
		
}
