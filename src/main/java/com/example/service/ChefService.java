package com.example.service;

import java.util.List;

import com.example.model.Chef;
import com.example.model.SystemUser;
import com.example.model.User;
import com.example.model.Waiter;

public interface ChefService {

	public Chef createChef(Chef newChef);
	
	public String updateChefProfile(Chef chef);
	
	public String updatePassword(Chef chef);

	public List<Chef> getAllChefs();
	
	public String logInChef(String email, String password);
	
	public String firstLogin(SystemUser systemUser);
	
	public Chef getLoggedIn();
}
