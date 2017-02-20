package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.model.Restaurant;
import com.example.model.TableRestaurant;

public interface RestaurantService {
	public String createRestaurant(Restaurant newRestaurant);
	
	public Page<Restaurant> getAllRestaurants();
	
	public List<TableRestaurant> getTables(long id);
}
