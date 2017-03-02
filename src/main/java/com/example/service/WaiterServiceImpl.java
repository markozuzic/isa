package com.example.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Manager;
import com.example.model.OrderR;
import com.example.model.Shift;
import com.example.model.TableRestaurant;
import com.example.model.User;
import com.example.model.Restaurant;
import com.example.model.SystemUser;
import com.example.model.Waiter;

import com.example.repository.OrderRepository;
import com.example.repository.ShiftRepository;
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
	private ShiftRepository shiftRepository;
	
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
		Waiter oldWaiter = (Waiter) httpSession.getAttribute("waiter");
		SystemUser oldSystemUser = systemUserRepository.findByEmail(oldWaiter.getEmail()).get(0);
		
		List<SystemUser> sameEmail = systemUserRepository.findByEmail(waiter.getEmail()); //sa novim emailom
		for(SystemUser su  : sameEmail) {
			if(su.getId() != oldSystemUser.getId()) {
				return "EmailError";
			}
		}
		
		//Waiter oldWaiter = waiterRepository.findOne(waiter.getId());
		oldWaiter.setBirthDate(waiter.getBirthDate());
		oldWaiter.setClothesSize(waiter.getClothesSize());
		oldWaiter.setLastname(waiter.getLastname());
		oldWaiter.setName(waiter.getName());
		oldWaiter.setShoeSize(waiter.getShoeSize());
		oldWaiter.setEmail(waiter.getEmail());
		oldWaiter.setPassword(waiter.getPassword());
		waiterRepository.save(oldWaiter);
		httpSession.setAttribute("waiter", oldWaiter);
		oldSystemUser.setEmail(waiter.getEmail());
		oldSystemUser.setPassword(waiter.getPassword());
		systemUserRepository.save(oldSystemUser);
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
			if(waiter.isFirstLogin()) {
				List<SystemUser> systemUser = systemUserRepository.findByEmail(email);
				long id = systemUser.get(0).getId();
				return "firstLogin,"+id;
			}
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
	
	@Override
	public List<Waiter> getAllWaiters() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getId());
		
		return waiterRepository.findByRestaurantId(r.getId());
	}

	@Override
	public String firstLogin(SystemUser systemUser) {
		Waiter waiter = waiterRepository.findByEmail(systemUser.getEmail()).get(0);
		waiter.setPassword(systemUser.getPassword());
		waiter.setFirstLogin(false);
		waiterRepository.save(waiter);
		systemUserRepository.save(systemUser);
		return "OK";
	}

	@Override
	public Waiter getLoggedIn() {
		return (Waiter) httpSession.getAttribute("waiter");
	}


	
}
