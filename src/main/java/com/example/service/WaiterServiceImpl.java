package com.example.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.OrderR;
import com.example.model.Shift;
import com.example.model.TableRestaurant;
import com.example.model.Waiter;
import com.example.repository.OrderRepository;
import com.example.repository.ShiftRepository;
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
	private ShiftRepository shiftRepository;
	
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

	@Override
	public String logInWaiter(Waiter waiter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TableRestaurant> getReon() {
		Waiter waiter = (Waiter)httpSession.getAttribute("waiter");
		List<Shift> shifts = shiftRepository.findByEmployeeTypeAndRestaurantId("waiter", waiter.getRestaurantId());
		ArrayList<TableRestaurant> tables = new ArrayList<TableRestaurant>();
		Calendar calendar = Calendar.getInstance();

	    calendar.setTime(new Date());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    Date dateTodayMidnight = calendar.getTime();
	    
		for (Shift shift : shifts) {
			calendar.setTime(shift.getDate());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date dateShiftMidnight = calendar.getTime();
			
			if(dateTodayMidnight.compareTo(dateShiftMidnight) == 0){  //u istom su danu 
				tables.addAll(shift.getReon());
				return tables;
			}
		}
		return tables;
	}

	@Override
	public List<OrderR> getUnfinishedOrders() {
		Waiter waiter = (Waiter)httpSession.getAttribute("waiter");
		return orderRepository.findByWaiterAndFinished(waiter, false);
	}
	
	
	

}
