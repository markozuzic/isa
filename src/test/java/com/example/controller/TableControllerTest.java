package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.repository.RestaurantRepository;
import com.example.repository.TableRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TableControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	protected MockHttpSession session;
	
	private MockMvc mvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		TableRestaurant table1 = new TableRestaurant();
		table1.setTableNumber(1);
		table1.setNumberOfChairs(4);
		table1.setRestaurant(1);
		table1.setX(5);
		table1.setY(3);
		table1.setSegment("garden");
		tableRepository.save(table1);
		
		TableRestaurant table2 = new TableRestaurant();
		table2.setTableNumber(2);
		table2.setNumberOfChairs(2);
		table2.setRestaurant(1);
		table2.setX(2);
		table2.setY(3);
		table2.setSegment("inside");
		tableRepository.save(table2);
		
		Restaurant restaurant1 = new Restaurant("r1", "opis1");
		restaurant1.setAddress("Puskinova 16 Novi Sad Srbija");
		restaurantRepository.save(restaurant1);
		
		
	}
	
	@Test
	public void testCreateTable() throws Exception {
		TableRestaurant table3 = new TableRestaurant();
		table3.setTableNumber(3);
		table3.setNumberOfChairs(2);
		table3.setRestaurant(1);
		table3.setSegment("inside");
		mvc.perform(post("/table/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsBytes(table3)))
	            .andExpect(status().isOk())
	            .andExpect(content().string("OK"));
	}
	
	@Test
	public void testGetTables() throws Exception {
		long id = 1;
		mvc.perform(get("/table/getTables/"+id))
		  		.andExpect(status().isOk())
		  		.andExpect(jsonPath("$[0].tableNumber", Matchers.is(1)))
		  		.andExpect(jsonPath("$[0].segment", Matchers.is("garden")))
		  		.andExpect(jsonPath("$[1].tableNumber", Matchers.is(2)))
		  		.andExpect(jsonPath("$[1].segment", Matchers.is("inside")));
	}
}
