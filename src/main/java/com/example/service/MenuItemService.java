package com.example.service;

import java.util.List;

import com.example.model.MenuItem;

public interface MenuItemService {

	public String createMenuItem(MenuItem newMenuItem);
	
	public List<MenuItem> getMenuItemsMeals(String type);
	
	public List<MenuItem> getMenuItemsDrinks(String type);
	
}
