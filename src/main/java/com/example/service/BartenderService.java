package com.example.service;

import java.util.List;

import com.example.model.Bartender;

public interface BartenderService {

	public Bartender createBartender(Bartender newBartender);
	
	public String updateBartenderProfile(Bartender bartender);
	
	public String updatePassword(Bartender bartender);

	public List<Bartender> getAllBartenders();

	public String logInBartender(String email, String password);
		
}
