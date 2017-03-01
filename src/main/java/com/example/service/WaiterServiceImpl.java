package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Manager;
import com.example.model.OrderR;
import com.example.model.Restaurant;
import com.example.model.SystemUser;
import com.example.model.Waiter;
import com.example.repository.OrderRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.SystemUserRepository;
import com.example.repository.WaiterRepository;

@Service
public class WaiterServiceImpl implements WaiterService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private WaiterRepository waiterRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	@Override
	public Waiter createWaiter(Waiter newWaiter) {
		
		if(systemUserRepository.findByEmail(newWaiter.getEmail()).isEmpty()){
			Manager m = (Manager) httpSession.getAttribute("manager");
			newWaiter.setRestaurantId(m.getRestaurantId());	
			SystemUser su = new SystemUser(newWaiter.getEmail(), newWaiter.getPassword(), "waiter");
			systemUserRepository.save(su);
			return waiterRepository.save(newWaiter);
		}
		else {
			newWaiter.setId(0);
			return newWaiter;
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
		if(waiterRepository.findByEmail(waiter.getEmail()).isEmpty()){
			return "EmailError";
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
	
	@Override
	public String logInWaiter(String email, String password) {
		List<Waiter> waiters = waiterRepository.findByEmail(email);
		if (waiters.isEmpty()) {
			return "EmailError";
		}
		else {
			Waiter waiter = waiters.get(0);
			if (waiter.getPassword().equals(password)) {
				httpSession.setAttribute("waiter", waiter);
				return "waiter";
			}
			else {
				return "PasswordError";
			}
		}
	}
	
	@Override
	public List<Waiter> getAllWaiters() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getId());
		
		return waiterRepository.findByRestaurantId(r.getId());
	}
	
}
