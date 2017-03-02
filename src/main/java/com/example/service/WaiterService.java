package com.example.service;


import java.util.ArrayList;
import java.util.List;

import com.example.model.OrderR;
import com.example.model.SystemUser;
import com.example.model.TableRestaurant;
import com.example.model.Waiter;

public interface WaiterService {

	public Waiter createWaiter(Waiter newWaiter);
	
	public String createOrder(OrderR newOrder);
	
	public String updateWaiterProfile(Waiter waiter);
	
	public String updatePassword(Waiter waiter);
	
	public ArrayList<TableRestaurant> getReon();
	
	public List<OrderR> getUnfinishedOrders();
	
	public List<Waiter> getAllWaiters();

	public String logInWaiter(String email, String password);

	public String firstLogin(SystemUser systemUser);

	public Waiter getLoggedIn();
	
}
