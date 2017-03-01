package com.example.service;

import java.util.List;

import com.example.model.Demand;

public interface DemandService {

	public List<Demand> getAllDemands();

	public String create(String idList, Demand d);
	
}
