package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Bartender;

public interface BartenderRepository extends Repository<Bartender, Long> {

	public Bartender save(Bartender entity);
	
	public void delete(Bartender entity);
	
	public Bartender findOne(long id);
	
	public List<Bartender> findById(long id);
	
	public List<Bartender> findByRestaurantId(long restaurantId);
	
	public List<Bartender> findByEmail(String email);
	
}
