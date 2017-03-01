package com.example.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.model.MenuItem;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.model.Waiter;
import com.example.repository.RestaurantRepository;
import com.example.repository.TableRepository;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	private static GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAKHME46MyyV-URGCHluThSfGjknTETQKE");
	
	@Override
	public String createRestaurant(Restaurant newRestaurant) {
		if(restaurantRepository.findByName(newRestaurant.getName()).isEmpty()) {
			
			
			GeocodingResult[] results;
			try {
				results = GeocodingApi.geocode(context, newRestaurant.getAddress()).await();
				newRestaurant.setLatitude(results[0].geometry.location.lat);
				newRestaurant.setLongitude(results[0].geometry.location.lng);
			} catch (Exception e) {
				newRestaurant.setLatitude(0);
				newRestaurant.setLongitude(0);
			}
			
			
			
			restaurantRepository.save(newRestaurant);
			return "OK";
		}
		return "NameError";
	}

	@Override
	public Page<Restaurant> getAllRestaurants() {
		// TODO Auto-generated method stub
		return restaurantRepository.findAll(null);
	}

	@Override
	public List<TableRestaurant> getTables() {
		long restaurantId = 0;
		if (httpSession.getAttribute("waiter") != null) {
			Waiter w = (Waiter) httpSession.getAttribute("waiter");
			restaurantId = w.getRestaurantId();
		} else if (httpSession.getAttribute("bartender") != null) {
			Bartender b = (Bartender) httpSession.getAttribute("bartender");
			restaurantId = b.getRestaurantId();
		} else if (httpSession.getAttribute("chef") != null) {
			Chef c = (Chef) httpSession.getAttribute("chef");
			restaurantId = c.getRestaurantId();
		}
		return tableRepository.findByRestaurant(restaurantId);
	}

	@Override
	public Collection<MenuItem> getAllMenuItems() {
		long restaurantId = 0;
		if (httpSession.getAttribute("waiter") != null) {
			Waiter w = (Waiter) httpSession.getAttribute("waiter");
			restaurantId = w.getRestaurantId();
		} else if (httpSession.getAttribute("bartender") != null) {
			Bartender b = (Bartender) httpSession.getAttribute("bartender");
			restaurantId = b.getRestaurantId();
		} else if (httpSession.getAttribute("chef") != null) {
			Chef c = (Chef) httpSession.getAttribute("chef");
			restaurantId = c.getRestaurantId();
		}
		ArrayList<MenuItem> retVal = new ArrayList<MenuItem>();
		retVal.addAll(restaurantRepository.findOne(restaurantId).getMenu());
		retVal.addAll(restaurantRepository.findOne(restaurantId).getDrinks());
		return retVal;
	}

	@Override
	public Restaurant getRestaurantForEmployee() {
		long restaurantId = 0;
		if (httpSession.getAttribute("waiter") != null) {
			Waiter w = (Waiter) httpSession.getAttribute("waiter");
			restaurantId = w.getRestaurantId();
		} else if (httpSession.getAttribute("bartender") != null) {
			Bartender b = (Bartender) httpSession.getAttribute("bartender");
			restaurantId = b.getRestaurantId();
		} else if (httpSession.getAttribute("chef") != null) {
			Chef c = (Chef) httpSession.getAttribute("chef");
			restaurantId = c.getRestaurantId();
		}
		return restaurantRepository.findOne(restaurantId);
	}
}
