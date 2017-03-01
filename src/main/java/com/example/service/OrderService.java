package com.example.service;

import java.util.ArrayList;
import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.pojo.PostData;

public interface OrderService {

	public String createOrder(OrderR order);

	public String createOrderFromReservation(PostData orderData, Long reservationId);
	
	public ArrayList<MenuItem> getAllMeals();	

	public ArrayList<MenuItem> getAllDrinks();

	public String generateReport(PostData dates);

	public String generateWaiterReport(long id);

}
