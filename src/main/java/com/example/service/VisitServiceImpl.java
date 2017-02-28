package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.Restaurant;
import com.example.model.Visit;
import com.example.model.Waiter;
import com.example.repository.MenuItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.VisitRepository;
import com.example.repository.WaiterRepository;

@Service
public class VisitServiceImpl implements VisitService {

	@Autowired 
	VisitRepository visitRepository;
	
	@Autowired 
	RestaurantRepository restaurantRepository;
	
	@Autowired 
	OrderRepository orderRepository;
	
	@Autowired
	MenuItemRepository menuItemRepository;
	
	@Autowired
	WaiterRepository waiterRepository;
	
	@Override
	public String rateRestaurant(Long visitId, String rating) {
		int ratingInt = Integer.parseInt(rating);
		Visit visit = visitRepository.findOne(visitId);
		Restaurant restaurant = restaurantRepository.findOne(visit.getReservation().getRestaurantId());
		int ratingCounter = restaurant.getRatingCounter();
		double actualRating = restaurant.getRating();
		
		double newRating = (ratingCounter*actualRating + ratingInt) / (ratingCounter+1);
		ratingCounter++;
		restaurant.setRatingCounter(ratingCounter);
		restaurant.setRating(newRating);
		restaurantRepository.save(restaurant);	
		visit.setRatedRestaurant(true);
		visitRepository.save(visit);
		return "OK";
	}

	@Override
	public String rateMeal(Long visitId, String rating) {
		int ratingInt = Integer.parseInt(rating);
		Visit visit = visitRepository.findOne(visitId);
		
		List<OrderR> orders = orderRepository.findByVisit(visit);
		
		for (OrderR orderR : orders) {
			for (MenuItem mi : orderR.getMenuItems()) {
				int ratingCounter = mi.getRatingCounter();
				double actualRating = mi.getRating();
				double newRating = (ratingCounter*actualRating + ratingInt) / (ratingCounter+1);
				ratingCounter++;
				mi.setRating(newRating);
				mi.setRatingCounter(ratingCounter);
				menuItemRepository.save(mi);
			}
			orderRepository.save(orderR);
		}
		visit.setRatedMeal(true);
		visitRepository.save(visit);
		return "OK";
	}

	@Override
	public String rateService(Long visitId, String rating) {
		int ratingInt = Integer.parseInt(rating);
		Visit visit = visitRepository.findOne(visitId);
		
		List<OrderR> orders = orderRepository.findByVisit(visit);
		
		for (OrderR orderR : orders) {
			if(orderR.getWaiter() != null) {
				Waiter waiter = orderR.getWaiter();
				int ratingCounter = waiter.getRatingCounter();
				double actualRating = waiter.getRating();
				double newRating = (ratingCounter*actualRating + ratingInt) / (ratingCounter+1);
				ratingCounter++;
				waiter.setRating(newRating);
				waiter.setRatingCounter(ratingCounter);
				waiterRepository.save(waiter);
			}
			orderRepository.save(orderR);
		}
		visit.setRatedService(true);
		visitRepository.save(visit);
		return "OK";
	}
	
	

}
