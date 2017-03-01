package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Manager;
import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.User;
import com.example.model.Visit;
import com.example.model.Waiter;
import com.example.model.pojo.PostData;
import com.example.repository.MenuItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.VisitRepository;
import com.example.repository.WaiterRepository;

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
	
	@Autowired
	private WaiterRepository waiterRepository;
	
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

	public ArrayList<MenuItem> getAllMeals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MenuItem> getAllDrinks() {
//		List<OrderR> tempOrders = orderRepository.findAll(null).getContent();
//		List<OrderR> copyOrders = null;
//		ArrayList<MenuItem> copyMenuItems = new ArrayList<MenuItem>();
//		for (int i = 0; i < tempOrders.size(); i++) {
//
//			List<MenuItem> tempMenuItems = tempOrders.get(i).getMenuItems();
//			
//			for (int j = 0; j < tempMenuItems.size(); i++) {
//				if (tempMenuItems.get(j).getType() == "drink") {
//					copyMenuItems.add(tempMenuItems.get(j));
//				}
//
//			}
//			
//			tempOrders.get(i).setMenuItems(copyMenuItems);
//		}
//			return copyMenuItems;
		return null;
	}

	@Override
	public String generateReport(PostData dates) {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Waiter w = waiterRepository.findByRestaurantId(m.getRestaurantId()).get(0);
		List<OrderR> orders = orderRepository.findByWaiter(w);
		Date date;
		Double priceSum = 0.0;
		
		for (OrderR order : orders) {
			date = order.getReservation().getReservation().getDateTime(); //prvi gerReservation treba da bude getVisit

			if (date.after(dates.getDateStart()) && date.before(dates.getDateEnd())) {
				 for (MenuItem mi : order.getMenuItems()) {
					 priceSum += mi.getPrice();
				}
			}
		}
		return priceSum.toString();
	}

	@Override
	public String generateWaiterReport(long id) {
		List<OrderR> orders = orderRepository.findByWaiter(waiterRepository.findOne(id));
		Double priceSum = 0.0;
		for (OrderR order : orders) {
			for (MenuItem mi : order.getMenuItems()) {
				priceSum += mi.getPrice();
			}
		}
		return priceSum.toString();
	}

}

	
	
