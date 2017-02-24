package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Chef;

public interface ChefRepository extends Repository<Chef, Long> {

	public Chef save(Chef entity);
	
	public void delete(Chef entity);
	
	public Chef findOne(long id);
	
	public List<Chef> findById(long id);
}
