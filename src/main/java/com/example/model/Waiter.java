package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Waiter {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String lastname;
	
	@Column
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date birthDate;
	
	@Column
	private int clothesSize;
	
	@Column
	private int shoeSize;
	
	@Column
	private String password;
	
	@Column
	private long restaurantId;
	
	@Column
	private int ratingCounter = 0;
	
	@Column 
	private double rating = 0;
		
	@Column
	private boolean firstLogin;

	@Column
	public String email;

	
	public Waiter() {

	}
	
	public Waiter( String name, String lastname, Date birthDate, int clothesSize, int shoeSize, long restaurantId, String email) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.clothesSize = clothesSize;
		this.shoeSize = shoeSize;
		this.restaurantId = restaurantId;
		this.email = email;
	}

	public Waiter(String name, String lastname, Date birthDate, int clothesSize, int shoeSize, String password) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.clothesSize = clothesSize;
		this.shoeSize = shoeSize;
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
	
	public int getClothesSize() {
		return clothesSize;
	}
	
	public void setClothesSize(int clothesSize) {
		this.clothesSize = clothesSize;
	}
	
	public int getShoeSize() {
		return shoeSize;
	}
	
	public void setShoeSize(int shoeSize) {
		this.shoeSize = shoeSize;
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
	
	public int getRatingCounter() {
		return ratingCounter;
	}

	public void setRatingCounter(int ratingCounter) {
		this.ratingCounter = ratingCounter;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

}
