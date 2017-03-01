package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.Reservation;
import com.example.model.Restaurant;
import com.example.model.User;
import com.example.model.Visit;
import com.example.model.Waiter;
import com.example.repository.MenuItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ReservationRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.UserRepository;
import com.example.repository.VisitRepository;
import com.example.repository.WaiterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private WaiterRepository waiterRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	protected MockHttpSession session;
	
	private MockMvc mvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		Waiter waiter1 = new Waiter("n1", "l1", new Date(), 10, 40);
		waiter1.setFirstLogin(false);
		waiter1.setPassword("password");
		waiter1.setRating(0);
		waiter1.setRatingCounter(0);
		waiter1.setRestaurantId(1);
		waiterRepository.save(waiter1);
		
		MenuItem mi1 = new MenuItem("midesc1", "mi1", 10, "meal");
		menuItemRepository.save(mi1);
		MenuItem mi2 = new MenuItem("midesc2", "mi2", 30, "meal");
		menuItemRepository.save(mi2);
		ArrayList<MenuItem> mis = new ArrayList<MenuItem>();
		mis.add(mi1);
		mis.add(mi2);
		
		User user = new User("user", "surname", "email", "password");
		user.setActivated(true);
		user.setId(1);
		userRepository.save(user);
		Restaurant restaurant = new Restaurant("r1", "opis1");
		restaurant.setAddress("Puskinova 16 Novi Sad Srbija");
		restaurantRepository.save(restaurant);
		
		Reservation reservation = new Reservation(1, new Date(), 2, 1);
		Reservation reservationDB = reservationRepository.save(reservation);
		
		Visit visit = new Visit(user, reservationDB, "r1");
		visitRepository.save(visit);
		
		OrderR order = new OrderR();
		order.setIsDoneImmediately(true);
		order.setMenuItems(mis);
		order.setReservation(visit);
		order.setWaiter(waiter1);
		orderRepository.save(order);
		
	}
	
	@Test
	public void testRateRestaurant() throws Exception {
		User user = new User("user", "surname", "email", "password");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long id = 1;
		mvc.perform(MockMvcRequestBuilders.post("/visit/rateRestaurant/"+id).sessionAttrs(sessionattr)
		    .contentType(MediaType.APPLICATION_JSON)
			.content("4"))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void testRateMeal() throws Exception {
		User user = new User("user", "surname", "email", "password");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long id = 1;
		mvc.perform(MockMvcRequestBuilders.post("/visit/rateMeal/"+id).sessionAttrs(sessionattr)
		    .contentType(MediaType.APPLICATION_JSON)
			.content("4"))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void testRateService() throws Exception {
		User user = new User("user", "surname", "email", "password");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long id = 1;
		mvc.perform(MockMvcRequestBuilders.post("/visit/rateService/"+id).sessionAttrs(sessionattr)
		    .contentType(MediaType.APPLICATION_JSON)
			.content("4"))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
}
