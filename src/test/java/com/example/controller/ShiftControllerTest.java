package com.example.controller;

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

import com.example.model.Restaurant;
import com.example.model.Shift;
import com.example.model.Waiter;
import com.example.repository.RestaurantRepository;
import com.example.repository.ShiftRepository;
import com.example.repository.WaiterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiftControllerTest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private WaiterRepository waiterRepository;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
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
		
		Restaurant restaurant1 = new Restaurant("r1", "opis1");
		restaurant1.setAddress("Puskinova 16 Novi Sad Srbija");
		restaurantRepository.save(restaurant1);
		
		Shift shift = new Shift(new Date(), "waiter", 1, "1");
		shift.setRestaurantId(1);
		shiftRepository.save(shift);
	}
	
	@Test
	public void testShiftCreate() throws Exception {
		Shift shift = new Shift(new Date(), "waiter", 1, "2");
		shift.setRestaurantId(1);
		
		mvc.perform(post("/shift/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsBytes(shift)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testGetAllShifts() throws Exception {
		Waiter waiter1 = new Waiter("n1", "l1", new Date(), 10, 40);
		waiter1.setRestaurantId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter1);
		mvc.perform(MockMvcRequestBuilders.get("/shift/getAllShifts").sessionAttrs(sessionattr))
	    	.andExpect(MockMvcResultMatchers.status().isOk())
	        .andExpect(jsonPath("$[0].id", Matchers.is(1)))
	        .andExpect(jsonPath("$[0].shiftType", Matchers.is("1")))
	        .andExpect(jsonPath("$[0].employeeType", Matchers.is("waiter")))
	        .andExpect(jsonPath("$[1].id", Matchers.is(2)))
		    .andExpect(jsonPath("$[1].shiftType", Matchers.is("2")))
		    .andExpect(jsonPath("$[1].employeeType", Matchers.is("waiter")));
	}
}
