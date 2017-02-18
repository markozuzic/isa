package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Table {
	@Id
	private int tableNumber;
	
	@Column(nullable = false)
	private String segment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Restaurant restaurant;
	
	@Column(nullable = false)
	private int numberOfChairs;
	
	public Table() {
		
	}
	
	public Table(int tableNumber, String segment, Restaurant restaurant, int numberOfChairs) {
		this.tableNumber = tableNumber;
		this.segment = segment;
		this.restaurant = restaurant;
		this.numberOfChairs = numberOfChairs;
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public int getNumberOfChairs() {
		return numberOfChairs;
	}

	public void setNumberOfChairs(int numberOfChairs) {
		this.numberOfChairs = numberOfChairs;
	}
		
}
