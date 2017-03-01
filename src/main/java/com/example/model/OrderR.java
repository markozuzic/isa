package com.example.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class OrderR {

	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private boolean doneImmediately;
	
	@ManyToOne
	private Visit visit;
							
	@ManyToOne
	private Waiter waiter;
	
	@ManyToMany
	private List<MenuItem> menuItems;
	
	@Column
	private Date date;
	
	@Column
	private boolean finished = false;
	
	@Column
	private long tableNumber = 0;
	
	public OrderR(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public boolean getIsDoneImmediately() {
		return doneImmediately;
	}

	public void setIsDoneImmediately(boolean doneImmediately) {
		this.doneImmediately = doneImmediately;
	}

	public Visit getReservation() {
		return visit;
	}

	public void setReservation(Visit visit) {
		this.visit = visit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean getFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public long getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(long tableNumber) {
		this.tableNumber = tableNumber;
	}

	
	
	
}
