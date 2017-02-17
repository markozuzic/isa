package com.example.service;

import com.example.model.Manager;

public interface ManagerService {
	
	public void createManager(Manager newManager);
	
	public String logInManager(Manager manager);
}
