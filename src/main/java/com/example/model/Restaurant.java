package com.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Restaurant {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
	private List<TableRestaurant> tables;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
	private List<MenuItem> menu;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
	private List<MenuItem> drinks;
	
	public Restaurant() {
		
	}

	public Restaurant(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TableRestaurant> getTables() {
		return tables;
	}

	public void setTables(List<TableRestaurant> tables) {
		this.tables = tables;
	}

	public List<MenuItem> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuItem> menu) {
		this.menu = menu;
	}

	public List<MenuItem> getDrinks() {
		return drinks;
	}

	public void setDrinks(List<MenuItem> drinks) {
		this.drinks = drinks;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
