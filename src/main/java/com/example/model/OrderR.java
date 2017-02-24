package com.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;



@Entity
public class OrderR {

	@Id
	@GeneratedValue
	private long id;
	
	
	@ManyToOne
	@JoinColumn
	private Waiter waiter;
	
	public OrderR(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	
	
}
