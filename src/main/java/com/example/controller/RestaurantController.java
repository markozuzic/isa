package com.example.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Restaurant;
import com.example.service.RestaurantService;
import com.example.service.TableService;

@RestController
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private TableService tableService;
	
	@RequestMapping(
			value = "/restaurant/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createRestaurant(@RequestBody Restaurant restaurant) {
		return restaurantService.createRestaurant(restaurant);
	}
	
	@RequestMapping(
			value = "/restaurant/getAllRestaurants",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Collection<Restaurant> getAllRestaurants() {
		return restaurantService.getAllRestaurants().getContent();
	}
	
}
