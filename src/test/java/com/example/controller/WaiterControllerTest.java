package com.example.controller;

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

import com.example.model.Manager;
import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.Restaurant;
import com.example.model.Shift;
import com.example.model.TableRestaurant;
import com.example.model.Waiter;
import com.example.repository.ManagerRepository;
import com.example.repository.MenuItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.ShiftRepository;
import com.example.repository.TableRepository;
import com.example.repository.WaiterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WaiterControllerTest {
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private WaiterRepository waiterRepository;
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	protected MockHttpSession session;
	
	private MockMvc mvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		Waiter waiter1 = new Waiter("n1","l1", new Date(), 10, 40, 1, "email");
		waiter1.setFirstLogin(false);
		waiter1.setPassword("password");
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
		table2.setNumberOfChairs(4);
		table2.setRestaurant(1);
		table2.setX(5);
		table2.setY(3);
		table2.setSegment("garden");
		tableRepository.save(table2);
		
		ArrayList<TableRestaurant> tables =  new ArrayList<TableRestaurant>();
		tables.add(table1);
		tables.add(table2);
		
		OrderR order = new OrderR();
		order.setTableNumber(1);
		order.setMenuItems(mis);
		order.setWaiter(waiter1);
		order.setDate(new Date());
		orderRepository.save(order);
		
		OrderR order2 = new OrderR();
		order2.setTableNumber(1);
		order2.setMenuItems(mis);
		order2.setWaiter(waiter1);
		order2.setDate(new Date());
		order2.setFinished(true);
		orderRepository.save(order2);
		
		Manager manager = new Manager("m", "pass", "restaurant");
		manager.setRestaurantId(1);
		managerRepository.save(manager);
		
		Shift shift = new Shift(new Date(), 1, "2", "waiter");
		shift.setRestaurantId(1);
		shift.setReon(tables);
		shiftRepository.save(shift);
		
		
	}
	
	
	@Test
	public void testCreateWaiter() throws Exception {
		Waiter waiter1 = new Waiter("n1","l1", new Date(), 10, 40, 1, "email");
		waiter1.setFirstLogin(false);
		waiter1.setPassword("password");
		Manager manager = new Manager("m", "pass", "restaurant");
		manager.setRestaurantId(1);
		
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("manager", manager);
		mvc.perform(MockMvcRequestBuilders.post("/waiter/create").sessionAttrs(sessionattr)
		    .contentType(MediaType.APPLICATION_JSON)
		    .content(objectMapper.writeValueAsBytes(waiter1)))
	    	.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void testGetUnfinishedOrders() throws Exception {
		Waiter waiter1 = new Waiter("n1","l1", new Date(), 10, 40, 1, "email");
		waiter1.setFirstLogin(false);
		waiter1.setPassword("password");
		waiter1.setId(1);
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter1);
		mvc.perform(MockMvcRequestBuilders.get("/waiter/getUnfinishedOrders").sessionAttrs(sessionattr))
		    .andExpect(MockMvcResultMatchers.status().isOk())
		    .andExpect(jsonPath("$[0].id", Matchers.is(1)));
	}
	
	
	@Test
	public void testUpdateProfile() throws Exception {
		Waiter waiter2 = new Waiter("newName","newLastName", new Date(), 10, 40, 1, "email");
		waiter2.setFirstLogin(false);
		waiter2.setPassword("password");
		
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter2);

		mvc.perform(MockMvcRequestBuilders.post("/waiter/updateProfile").sessionAttrs(sessionattr)
	     	.contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsBytes(waiter2)))
		    .andExpect(status().isOk());
	}
	
	@Test
	public void testGetLoggedIn() throws Exception{
		Waiter waiter1 = new Waiter("n1","l1", new Date(), 10, 40, 1, "email");
		waiter1.setId(1);
		waiter1.setFirstLogin(false);
		waiter1.setPassword("password");
		
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter1);

		mvc.perform(MockMvcRequestBuilders.get("/waiter/getLoggedIn").sessionAttrs(sessionattr))
	     	 .andExpect(status().isOk())
	     	 .andExpect(jsonPath("$.id", Matchers.is(1)));
		
	}
	
	@Test
	public void testGetReon() throws Exception {
		Waiter waiter1 = new Waiter("n1","l1", new Date(), 10, 40, 1, "email");
		waiter1.setId(1);
		waiter1.setFirstLogin(false);
		waiter1.setPassword("password");
		
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("waiter", waiter1);

		mvc.perform(MockMvcRequestBuilders.get("/waiter/getReon").sessionAttrs(sessionattr))
	     	 .andExpect(status().isOk())
	     	 .andExpect(jsonPath("$[0].tableNumber", Matchers.is(1)))
		     .andExpect(jsonPath("$[1].tableNumber", Matchers.is(2)));
	}

}
