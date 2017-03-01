package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.OrderR;
import com.example.model.Waiter;
import com.example.service.OrderService;
import com.example.service.WaiterService;

@RestController
public class WaiterController {

	@Autowired
	private WaiterService waiterService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(
			value = "/waiter/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
		public Waiter createWaiter(@RequestBody Waiter waiter){
		return waiterService.createWaiter(waiter);
	}
	
	@RequestMapping(
			value = "/waiter/createOrder",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createOrder(@RequestBody OrderR order){
		return orderService.createOrder(order);
	}
	
	@RequestMapping(
			value = "/waiter/updateProfile",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String UpdateWaiterProfile(@RequestBody Waiter waiter){
		String response =  waiterService.updateWaiterProfile(waiter);
		return response;
	}
	
	@RequestMapping(
			value = "/waiter/updatePassword",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String updateWaiterPassword(@RequestBody Waiter waiter){
		String response = waiterService.updatePassword(waiter);
		return response;
	}
	
	@RequestMapping(
			value = "/waiter/getWaiters",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<Waiter> getAllWaiters() {
		return waiterService.getAllWaiters();
	}
	
}
