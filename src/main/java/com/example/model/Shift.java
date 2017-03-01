package com.example.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Shift {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String shiftType;	//first or second (third?)
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false)
	private String employeeType;
	
	@Column(nullable = false)
	private long employeeId;
	
	@Column
	private long restaurantId;
	
	@ManyToMany
	private List<TableRestaurant> reon;
	
	public Shift() {
		
	}
	
	public Shift(Date date, String employeeType, long employeeId, String shiftType) {
		super();
		this.date = date;
		this.employeeType = employeeType;
		this.employeeId = employeeId;
		this.shiftType = shiftType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public long getId() {
		return id;
	}

	public List<TableRestaurant> getReon() {
		return reon;
	}

	public void setReon(List<TableRestaurant> reon) {
		this.reon = reon;
	}

	public String getShiftType() {
		return shiftType;
	}

	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
}
