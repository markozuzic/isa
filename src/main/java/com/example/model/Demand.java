package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Demand {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date beginDate;
	
	@Column
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date endDate;
	
	@ManyToMany
	private List<DemandItem> listOfItems;
	
	@Column
	private long restaurantId;
	
	@Column
	private boolean isActive = true;
	
	public Demand() {
		
	}
	
	public Demand(Date beginDate, Date endDate, long restaurantId) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.restaurantId = restaurantId;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DemandItem> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(List<DemandItem> listOfItems) {
		this.listOfItems = listOfItems;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
