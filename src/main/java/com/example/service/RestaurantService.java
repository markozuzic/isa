package com.example.service;

import org.springframework.data.domain.Page;

import com.example.model.Restaurant;

public interface RestaurantService {
	public String createRestaurant(Restaurant newRestaurant);
	
	public Page<Restaurant> getAllRestaurants();
}
