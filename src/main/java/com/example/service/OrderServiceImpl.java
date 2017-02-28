package com.example.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.User;
import com.example.model.Visit;
import com.example.model.pojo.PostData;
import com.example.repository.MenuItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.VisitRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired 
	private VisitRepository visitRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
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
	public String createOrderFromReservation(PostData orderData, Long reservationId) {
		User user = (User)httpSession.getAttribute("user");
		Visit visit = visitRepository.findByUserIdAndReservationId(user.getId(), reservationId).get(0);
		String items = orderData.getItems();
		String tokens[] = items.split(",");
		OrderR order = orderRepository.save(new OrderR());
		order.setMenuItems(new ArrayList<MenuItem>());
		order.setReservation(visit);
		for (String string : tokens) {
			MenuItem mi = menuItemRepository.findOne(Long.parseLong(string));
			order.getMenuItems().add(mi);
		}
		if(orderData.getFlag().equals("true"))
			order.setIsDoneImmediately(true);
		else
			order.setIsDoneImmediately(false);
		orderRepository.save(order);
		return "OK";
	}

}
