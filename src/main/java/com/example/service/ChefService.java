package com.example.service;

import java.util.List;

import com.example.model.Chef;

public interface ChefService {

	public Chef createChef(Chef newChef);
	
	public String updateChefProfile(Chef chef);
	
	public String updatePassword(Chef chef);

	public List<Chef> getAllChefs();
	
}
