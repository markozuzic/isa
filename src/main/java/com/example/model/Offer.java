package com.example.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Offer {

	@Id
	@GeneratedValue
	private long id;
	
	@OneToMany
	private List<MenuItem> neededItems;
	
	@Column(nullable = false)
	private Date endDate;
	
	public Offer() {
		
	}

	public Offer(List<MenuItem> neededItems, Date endDate) {
		super();
		this.neededItems = neededItems;
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public List<MenuItem> getNeededItems() {
		return neededItems;
	}

	public void setNeededItems(List<MenuItem> neededItems) {
		this.neededItems = neededItems;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
}
