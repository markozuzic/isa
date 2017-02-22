package com.example.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.OrderR;
import com.example.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public String createOrder(OrderR newOrder) {
		if(orderRepository.findById(newOrder.getId()).isEmpty()){
			orderRepository.save(newOrder);
			return "OK";
		}
		else{
			return "IdError";
		}
	}

}
