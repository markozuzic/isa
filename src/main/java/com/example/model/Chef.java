package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Chef {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String lastname;
	
	@Column
	private Date birthDate;
	
	@Column
	private int shoeSize;
	
	@Column
	private int clothesSize;
	
	@Column
	private String password;
	
	@Column
	private long restaurantId;
	
	@Column
	private boolean firstLogin;

	public Chef() {
		
	}

	public Chef(String name, String lastname, Date birthDate, int shoeSize, int clothesSize, String password) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.shoeSize = shoeSize;
		this.clothesSize = clothesSize;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getShoeSize() {
		return shoeSize;
	}

	public void setShoeSize(int shoeSize) {
		this.shoeSize = shoeSize;
	}

	public int getClothesSize() {
		return clothesSize;
	}

	public void setClothesSize(int clothesSize) {
		this.clothesSize = clothesSize;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	
	
	
	
	
}
