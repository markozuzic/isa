package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Visit {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Reservation reservation;
	

	public Visit(){}

	public Visit(User user, Reservation reservation) {
		super();
		this.user = user;
		this.reservation = reservation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	
	
}
