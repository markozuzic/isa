package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.Restaurant;

public interface RestaurantRepository extends Repository<Restaurant, Long>{
	
	public Restaurant save(Restaurant entity);
	
	public List<Restaurant> findByName(String name);
	
	public Restaurant findOne(long id);
	
	public Page<Restaurant> findAll(Pageable pageable);
	
}
