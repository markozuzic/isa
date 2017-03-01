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
import com.example.model.User;
import com.example.model.Visit;
import com.example.repository.FriendshipRepository;
import com.example.repository.ReservationRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.UserRepository;
import com.example.repository.VisitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

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
	private FriendshipRepository friendshipRepository;
	
	protected MockHttpSession session;
	
	private MockMvc mvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		user.setActivated(true);
		User userDB = userRepository.save(user);
		
		User activateUser = new User("activatorName", "activatorSurname", "activatorEmail", "activatorPassword");
		activateUser.setId(2);
		activateUser.setActivated(false);
		activateUser.setActivationCode("activationCode");
		userRepository.save(activateUser);
		
		User user3 = new User("n3", "s3", "email3", "password3");
		user3.setActivated(true);
		user3.setId(3);
		User user4 = new User("n4", "s4", "email4", "password4");
		user4.setId(4);
		user4.setActivated(true);
		User user5 = new User("n5", "s5", "email5", "password5");
		user5.setId(5);
		user5.setActivated(true);
		userRepository.save(user3);
		userRepository.save(user4);
		userRepository.save(user5);
		Restaurant restaurant = new Restaurant("r1", "opis1");
		restaurant.setAddress("Puskinova 16 Novi Sad Srbija");
		restaurantRepository.save(restaurant);
		
		Reservation reservation = new Reservation(1, new Date(), 2, 1);
		Reservation reservationDB = reservationRepository.save(reservation);
		
		Visit visit = new Visit(userDB, reservationDB, "r1");
		visitRepository.save(visit);
		
		FriendRequest fr1 = new FriendRequest(2, 1, "pending");
		FriendRequest fr2 = new FriendRequest(3, 1, "pending");
		FriendRequest fr3 = new FriendRequest(4, 1, "pending");
		FriendRequest fr4 = new FriendRequest(5, 1, "accepted");
		friendshipRepository.save(fr1);
		friendshipRepository.save(fr2);
		friendshipRepository.save(fr3);
		friendshipRepository.save(fr4);
		
	}
	
	
	@Test
	public void testRegister() throws Exception {
		User user = new User("mockName", "mockSurname", "mockEmail", "mockPassword");
		
		mvc.perform(post("/user/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsBytes(user)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testLogin() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		mvc.perform(post("/user/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsBytes(user)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testActivate() throws Exception {
		
		User activateUser = new User("activatorName", "activatorSurname", "activatorEmail", "activatorPassword");
		activateUser.setActivated(false);
		activateUser.setActivationCode("activationCode");

		mvc.perform(post("/user/activateAccount")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsBytes(activateUser)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testGetLoggedIn() throws Exception {
		mvc.perform(get("/user/getLoggedInUser"))
	            .andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateUserInfo() throws Exception {
		  User user = new User("updateName", "updateSurname", "activatorEmail", "activatorPassword");
		  user.setId(2);
		  mvc.perform(post("/user/updateUserInfo")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsBytes(user)))
		            .andExpect(status().isOk())
		            .andExpect(content().string("OK"));
	}
	 
	@Test
	public void testGetAllUsers() throws Exception {
		  mvc.perform(get("/user/getAllUsers"))
				   .andExpect(status().isOk())
				   .andExpect(jsonPath("$[0].id", Matchers.is(1)))
	               .andExpect(jsonPath("$[0].email", Matchers.is("loggerEmail")))
	               .andExpect(jsonPath("$[0].password", Matchers.is("loggerPassword")))
		  		   .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                   .andExpect(jsonPath("$[1].email", Matchers.is("activatorEmail")))
                   .andExpect(jsonPath("$[1].password", Matchers.is("activatorPassword")))
		  		   .andExpect(jsonPath("$[2].id", Matchers.is(3)))
		           .andExpect(jsonPath("$[2].email", Matchers.is("email3")))
		           .andExpect(jsonPath("$[2].password", Matchers.is("password3")))
				   .andExpect(jsonPath("$[3].id", Matchers.is(4)))
		           .andExpect(jsonPath("$[3].email", Matchers.is("email4")))
		           .andExpect(jsonPath("$[3].password", Matchers.is("password4")));
 }
	
	
	@Test
	public void testLoginFromInvitation() throws Exception {
		long id = 1;
		mvc.perform(get("/user/loginFromInvitation/"+id))
			   .andExpect(status().isOk());
	}

	@Test
	public void testAddFriend() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long id = 2;
		mvc.perform(MockMvcRequestBuilders.get("/user/addFriend/"+id).sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	public void testRemoveFriend() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long id = 5;
		mvc.perform(MockMvcRequestBuilders.get("/user/removeFriend/"+id).sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGetFriends() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		mvc.perform(MockMvcRequestBuilders.get("/user/getFriends").sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGetVisits() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		mvc.perform(MockMvcRequestBuilders.get("/user/getVisits").sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$[0].user.id", Matchers.is(1)))
            .andExpect(jsonPath("$[0].restaurantName", Matchers.is("r1")));
	}
	
	@Test
	public void testGetPendingRequests() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		mvc.perform(MockMvcRequestBuilders.get("/user/getPendingRequests").sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	public void testAcceptFriendRequest() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long requestId = 3;
		mvc.perform(MockMvcRequestBuilders.get("/user/acceptFriendRequest/"+requestId).sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	public void testDenyFriendRequest() throws Exception {
		User user = new User("loggerName", "loggerSurname", "loggerEmail", "loggerPassword");
		user.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("user", user);
		long requestId = 2;
		mvc.perform(MockMvcRequestBuilders.get("/user/denyFriendRequest/"+requestId).sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

