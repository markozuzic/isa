package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;

import org.hamcrest.Matchers;
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

import com.example.model.FriendRequest;
import com.example.model.Reservation;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.model.User;
import com.example.model.Visit;
import com.example.repository.FriendshipRepository;
import com.example.repository.ReservationRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.TableRepository;
import com.example.repository.UserRepository;
import com.example.repository.VisitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	protected MockHttpSession session;
	
	private MockMvc mvc;
	
	@Autowired
    ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		
		User user1 = new User("n1", "s1", "email1", "password1");
		user1.setActivated(true);
		user1.setId(1);
		User user2 = new User("n2", "s2", "email2", "password2");
		user2.setId(2);
		user2.setActivated(true);
		User user3 = new User("n3", "s3", "email3", "password3");
		user3.setId(3);
		user3.setActivated(true);
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		Restaurant restaurant = new Restaurant("r1", "opis1");
		restaurant.setAddress("Puskinova 16 Novi Sad Srbija");
		restaurantRepository.save(restaurant);
		
		Reservation reservation1 = new Reservation(1, new Date(), 2, 1);
		reservationRepository.save(reservation1);
		Reservation reservation2 = new Reservation(1, new Date(), 3, 2);
		reservationRepository.save(reservation2);
		Reservation reservation3 = new Reservation(1, new Date(), 4, 3);
		reservationRepository.save(reservation3);
		
		TableRestaurant table1 = new TableRestaurant(1, "garden", 1, 2, 3, 4);
		TableRestaurant table2 = new TableRestaurant(2, "smokers", 1, 4, 1, 5);
		TableRestaurant table3 = new TableRestaurant(3, "non_smokers", 5, 2, 3, 2);
		tableRepository.save(table1);
		tableRepository.save(table2);
		tableRepository.save(table3);
		Visit visit1 = new Visit(user1, reservation1, "r1");
		visitRepository.save(visit1);
		Visit visit2 = new Visit(user2, reservation2, "r1");
		visitRepository.save(visit2);
		Visit visit3 = new Visit(user3, reservation3, "r1");
		visitRepository.save(visit3);
		
		FriendRequest fr1 = new FriendRequest(2, 1, "accepted");
		FriendRequest fr2 = new FriendRequest(3, 1, "accepted");
		FriendRequest fr3 = new FriendRequest(2, 3, "accepted");
		friendshipRepository.save(fr1);
		friendshipRepository.save(fr2);
		friendshipRepository.save(fr3);
		
	}
	
	@Test
	public void testGetReservations() throws Exception {
		 mvc.perform(get("/reservation/getAllReservations"))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$[0].id", Matchers.is(1)))
         .andExpect(jsonPath("$[0].restaurantId", Matchers.is(1)))
         .andExpect(jsonPath("$[0].length", Matchers.is(2)))
         .andExpect(jsonPath("$[0].userId", Matchers.is(1)))
		 .andExpect(jsonPath("$[1].id", Matchers.is(2)))
         .andExpect(jsonPath("$[1].restaurantId", Matchers.is(1)))
         .andExpect(jsonPath("$[1].length", Matchers.is(3)))
         .andExpect(jsonPath("$[1].userId", Matchers.is(2)))
		 .andExpect(jsonPath("$[2].id", Matchers.is(3)))
         .andExpect(jsonPath("$[2].restaurantId", Matchers.is(1)))
         .andExpect(jsonPath("$[2].length", Matchers.is(4)))
         .andExpect(jsonPath("$[2].userId", Matchers.is(3)));
	
	
	}
	
	@Test
	public void testGetReservation() throws Exception {
		 long reserId = 2;
		 mvc.perform(get("/reservation/getReservation/"+reserId))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$.id", Matchers.is(2)))
         .andExpect(jsonPath("$.restaurantId", Matchers.is(1)))
         .andExpect(jsonPath("$.length", Matchers.is(3)))
         .andExpect(jsonPath("$.userId", Matchers.is(2)));
	}
	
	@Test
	public void testSetTables() throws Exception {
		  long reserId = 1;
		  String tables = "1,2"; 
		  mvc.perform(post("/reservation/setTables/"+reserId)
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(tables))
		            .andExpect(status().isOk())
		            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testGetAvailableTables() throws Exception {
		 long reserId = 2;
		 mvc.perform(get("/reservation/getAvailableTables/"+reserId))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$[0].tableNumber", Matchers.is(1)));
	}
	
	@Test
	public void testGetRestaurantForReservation() throws Exception {
		 long reserId = 1;
		 mvc.perform(get("/reservation/getRestaurant/"+reserId))
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$.name", Matchers.is("r1")));
	}
	
	@Test
	public void testCancelReservation() throws Exception {
		 long reserId = 3;
		 mvc.perform(get("/reservation/cancel/"+reserId))
		 .andExpect(status().isOk())
		 .andExpect(content().string("OK"));
	}
	
	
	@Test
	public void testInviteFriend() throws Exception {
		
		User user = new User("n2", "s2", "email2", "password2");
		user.setId(2);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long reserId = 2;
		long friendId = 3;
		mvc.perform(MockMvcRequestBuilders.get("/reservation/inviteFriend/"+reserId+"/"+ friendId).sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	    	.andExpect(content().string("OK"));
	}
	
}
