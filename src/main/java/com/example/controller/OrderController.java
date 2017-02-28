package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.OrderR;
import com.example.model.pojo.PostData;
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
			value = "/order/createFromReservation/{reservationId}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createFromReservation(@RequestBody PostData order, @PathVariable("reservationId") Long reservationId){
		return orderService.createOrderFromReservation(order, reservationId);
	}
	
}
