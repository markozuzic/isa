package com.example.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.MenuItem;
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
	
	 @RequestMapping(
				value = "/order/getAllMeals",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
	public Collection<MenuItem> getAllMeals() {
		   return orderService.getAllMeals();
	}
	 
	@RequestMapping(
				value = "/employee/getAllDrinks",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
	public ArrayList<MenuItem> getAllOrders() {
		return orderService.getAllDrinks();
	}
	 	 
	 @RequestMapping(
				value = "/order/createBill/{orderId}",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
	 public String createBill(@PathVariable("orderId") Long orderId) {
		   return orderService.createBill(orderId);
	 } 
	 
	 @RequestMapping(
				value = "/order/new",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
	 public OrderR createNew(@RequestBody PostData postData) {
		   return orderService.createOrderFromPostData(postData);
	 }
	 
	 @RequestMapping(
				value = "/order/getUnfinishedOrders",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
	 public Collection<OrderR> getUnfinishedOrders() {
		   return orderService.getUnfinishedOrders();
	 }

	@RequestMapping(
			 	value = "/order/generateReport",
			 	method = RequestMethod.POST,
			 	produces = MediaType.TEXT_PLAIN_VALUE,
			 	consumes = MediaType.APPLICATION_JSON_VALUE
			 	)
	public String generateReport(@RequestBody PostData dates ) {
		return orderService.generateReport(dates);
	}
	
	@RequestMapping(
				value = "/order/generateReportForWaiter/{id}",
				method = RequestMethod.GET,
				produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String generateWaiterReport(@PathVariable long id) {
		return orderService.generateWaiterReport(id);
	}

}
