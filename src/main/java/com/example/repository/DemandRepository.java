package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Demand;

public interface DemandRepository extends Repository<Demand, Long> {

	public List<Demand> findByRestaurantId(long id);
	
	public Demand save(Demand newDemand);

	public Demand findOne(long demandId);

	public List<Demand> findByIsActive(boolean isActive);
	
}
