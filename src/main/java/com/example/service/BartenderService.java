package com.example.service;

import java.util.List;

import com.example.model.Bartender;
import com.example.model.SystemUser;
import com.example.model.Waiter;

public interface BartenderService {

	public Bartender createBartender(Bartender newBartender);
	
	public String updateBartenderProfile(Bartender bartender);
	
	public String updatePassword(Bartender bartender);

	public List<Bartender> getAllBartenders();

	public String logInBartender(String email, String password);
	
	public String firstLogin(SystemUser systemUser);
	
	public Bartender getLoggedIn();
		
}
