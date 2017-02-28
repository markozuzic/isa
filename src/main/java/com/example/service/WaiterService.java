package com.example.service;

import com.example.model.Bartender;
import com.example.model.OrderR;
import com.example.model.Waiter;

public interface WaiterService {

	public String createWaiter(Waiter newWaiter);
	
	public String createOrder(OrderR newOrder);
	
	public String updateWaiterProfile(Waiter waiter);
	
	public String updatePassword(Waiter waiter);
	
	public String logInWaiter(Waiter waiter);
	
}
