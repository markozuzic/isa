package com.example.service;

import com.example.model.OrderR;
import com.example.model.pojo.PostData;

public interface OrderService {

	public String createOrder(OrderR order);

	public String createOrderFromReservation(PostData orderData, Long reservationId);
	
}
