package com.example.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.User;
import com.example.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(
			value = "/order/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String CreateOrder(@RequestBody OrderR order){
		return orderService.createOrder(order);
	}
	
	 @RequestMapping(
				value = "/employee/getAllMeals",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ArrayList<MenuItem> getAllMeals(){
		    return orderService.getAllMeals();
		}
	 
	 @RequestMapping(
				value = "/employee/getAllDrinks",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ArrayList<MenuItem> getAllOrders(){
		    return orderService.getAllDrinks();
		}
}
