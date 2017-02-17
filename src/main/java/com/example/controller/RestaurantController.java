package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Restaurant;
import com.example.service.RestaurantService;

@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;
	
	@RequestMapping(
			value = "/restaurant/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String createRestaurant(@RequestBody Restaurant restaurant) {
		return restaurantService.createRestaurant(restaurant);
	}
}
