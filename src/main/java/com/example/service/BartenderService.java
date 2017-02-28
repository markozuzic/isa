package com.example.service;

import javax.servlet.http.HttpSession;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.model.User;
import com.example.repository.BartenderRepository;

public interface BartenderService {

	public String createBartender(Bartender newBartender);
	
	public String updateBartenderProfile(Bartender bartender);
	
	public String updatePassword(Bartender bartender);
	
	public String logInBartender(Bartender bartender);
		
}
