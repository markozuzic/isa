package com.example.service;

import java.util.List;

import com.example.model.MenuItem;

public interface MenuItemService {

	public MenuItem createMenuItem(MenuItem newMenuItem);

	public MenuItem createDrinkItem(MenuItem newMenuItem);

	public String removeDrinkItem(Long id);

	public String removeMenuItem(Long id);
	
	public List<MenuItem> getMenuItemsMeals(String type);
	
	public List<MenuItem> getMenuItemsDrinks(String type);
	
}
