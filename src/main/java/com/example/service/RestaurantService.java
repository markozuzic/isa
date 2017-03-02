package com.example.service;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;

import com.example.model.MenuItem;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;

public interface RestaurantService {
	public String createRestaurant(Restaurant newRestaurant);
	
	public Page<Restaurant> getAllRestaurants();

	public Restaurant getRestaurant();

	public String updateRestaurant(Restaurant updatedRestaurant);

	public List<TableRestaurant> getTables();

	public Collection<MenuItem> getAllMenuItems();

	public Restaurant getRestaurantForEmployee();
	
}
