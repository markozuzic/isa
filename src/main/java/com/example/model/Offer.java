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
	
	@Column
	private long supplierId;
	
	@Column
	private long demandId;
	
	@Column 
	private double price;
	
	@Column
	private boolean responded = false;
	
	

	public Offer() {
		
	}
	
	public Offer(Date endDate, long supplierId, long demandId, double price) {
		super();
		this.endDate = endDate;
		this.supplierId = supplierId;
		this.demandId = demandId;
		this.price = price;
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

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getDemandId() {
		return demandId;
	}

	public void setDemandId(long demandId) {
		this.demandId = demandId;
	}
	
	public boolean getResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}
}
