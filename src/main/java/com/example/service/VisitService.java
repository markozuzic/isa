package com.example.service;

public interface VisitService {
	public String rateRestaurant(Long visitId, String rating);

	public String rateMeal(Long visitId, String rating);

	public String rateService(Long visitId, String rating);
}	
