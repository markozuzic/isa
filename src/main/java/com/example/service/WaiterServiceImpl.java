package com.example.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.OrderR;
import com.example.model.Waiter;
import com.example.repository.OrderRepository;
import com.example.repository.WaiterRepository;

@Service
public class WaiterServiceImpl implements WaiterService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private WaiterRepository waiterRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public String createWaiter(Waiter newWaiter) {
		if(waiterRepository.findById(newWaiter.getId()).isEmpty()){
			waiterRepository.save(newWaiter);
			return "OK";
		}
		else
		{
			return "IdError";
		}
	}

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

	@Override
	public String updateWaiterProfile(Waiter waiter) {
		if(waiterRepository.findById(waiter.getId()).isEmpty()){
			return "IdError";
		}
		
		Waiter oldWaiter = waiterRepository.findOne(waiter.getId());
		oldWaiter.setBirthDate(waiter.getBirthDate());
		oldWaiter.setClothesSize(waiter.getClothesSize());
		oldWaiter.setLastname(waiter.getLastname());
		oldWaiter.setName(waiter.getName());
		oldWaiter.setShoeSize(waiter.getShoeSize());
		
		waiterRepository.save(oldWaiter);
		httpSession.setAttribute("waiter", oldWaiter);
		
		return "OK";
	}

	@Override
	public String updatePassword(Waiter waiter) {
		Waiter oldWaiter = waiterRepository.findOne(waiter.getId());
		
		oldWaiter.setPassword(waiter.getPassword());
		httpSession.setAttribute("waiter", oldWaiter);
		
		return "OK";
	}
	
	
	

}
