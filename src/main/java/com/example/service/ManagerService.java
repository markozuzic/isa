package com.example.service;

import java.util.List;

import com.example.model.Demand;
import com.example.model.Manager;

public interface ManagerService {
	
	public String createManager(Manager newManager, Long restaurantId);
	
	public String createManager(Manager newManager);
	
	public String logInManager(String email, String password);
	
	public Manager getLoggedIn();

	public List<Demand> getMyDemands();
}
