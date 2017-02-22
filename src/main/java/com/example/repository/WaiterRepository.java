package com.example.repository;


import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Waiter;

public interface WaiterRepository extends Repository<Waiter, Long> {

	public Waiter save(Waiter entity);
	
	public void delete(Waiter entity);
	
	public Waiter findOne(Long id);
	
	public List<Waiter> findById(Long id);
	
	
}
