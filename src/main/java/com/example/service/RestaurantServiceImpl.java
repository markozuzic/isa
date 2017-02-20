package com.example.service;

import java.util.List;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.repository.RestaurantRepository;
import com.example.repository.TableRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Override
	public String createRestaurant(Restaurant newRestaurant) {
		if(restaurantRepository.findByName(newRestaurant.getName()).isEmpty()) {
			restaurantRepository.save(newRestaurant);
			return "OK";
		}
		return "NameError";
	}

	@Override
	public Page<Restaurant> getAllRestaurants() {
		// TODO Auto-generated method stub
		return restaurantRepository.findAll(null);
	}

}
