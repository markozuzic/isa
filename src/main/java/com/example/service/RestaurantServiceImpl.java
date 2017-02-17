package com.example.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Restaurant;
import com.example.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public String createRestaurant(Restaurant newRestaurant) {
		if(restaurantRepository.findByName(newRestaurant.getName()).isEmpty()) {
			restaurantRepository.save(newRestaurant);
			return "OK";
		}
		return "NameError";
	}

}
