package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import com.example.model.MenuItem;
import com.example.model.Restaurant;
import com.example.model.Waiter;
import com.example.repository.MenuItemRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.WaiterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private WaiterRepository waiterRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
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
		
		Restaurant restaurant1 = new Restaurant("r1", "opis1");
		restaurant1.setAddress("Puskinova 16 Novi Sad Srbija");
		restaurant1.setMenu(mis);
		restaurantRepository.save(restaurant1);
		
		Restaurant restaurant2 = new Restaurant("r2", "opis2");
		restaurant2.setAddress("Doza Djerdja 20 Novi Sad Srbija");
		restaurantRepository.save(restaurant2);
		
		Restaurant restaurant3 = new Restaurant("r3", "opis3");
		restaurant3.setAddress("Tolstojeva 3 Novi Sad Srbija");
		restaurantRepository.save(restaurant3);
	}
	
	@Test
	public void testCreateRestaurant() throws Exception {
		Restaurant restaurant4 = new Restaurant("restoran", "opis4");
		restaurant4.setAddress("Puskinova 10 Novi Sad Srbija");
		
		mvc.perform(post("/restaurant/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsBytes(restaurant4)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testGetAllRestaurants() throws Exception {
		 mvc.perform(get("/restaurant/getAllRestaurants"))
		  .andExpect(status().isOk())
		  .andExpect(jsonPath("$[0].id", Matchers.is(1)))
          .andExpect(jsonPath("$[0].name", Matchers.is("r1")))
          .andExpect(jsonPath("$[1].id", Matchers.is(2)))
          .andExpect(jsonPath("$[1].name", Matchers.is("r2")))
          .andExpect(jsonPath("$[2].id", Matchers.is(3)))
          .andExpect(jsonPath("$[2].name", Matchers.is("r3")));
	}
	
	@Test
	public void testGetAllMenuItems() throws Exception{
		Waiter waiter1 = new Waiter("n1", "l1", new Date(), 10, 40);
		waiter1.setRestaurantId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter1);
		mvc.perform(MockMvcRequestBuilders.get("/restaurant/getAllMenuItems").sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(jsonPath("$[0].name", Matchers.is("mi1")))
	        .andExpect(jsonPath("$[0].type", Matchers.is("meal")))
	        .andExpect(jsonPath("$[1].name", Matchers.is("mi2")))
		    .andExpect(jsonPath("$[1].type", Matchers.is("meal")));
	}
	
	@Test
	public void testGetRestaurantForEmployee() throws Exception {
		Waiter waiter1 = new Waiter("n1", "l1", new Date(), 10, 40);
		waiter1.setRestaurantId(1L);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter1);
		mvc.perform(MockMvcRequestBuilders.get("/restaurant/getRestaurantForEmployee").sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(jsonPath("$.name", Matchers.is("r1")));
	}
}
