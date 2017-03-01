package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.TableRestaurant;

public interface TableRepository extends Repository<TableRestaurant, Integer> {
	
	public TableRestaurant save(TableRestaurant entity);
	
	public TableRestaurant findOne(int id);
	
	public List<TableRestaurant> findByRestaurant(long restaurant);
	
	public void delete(TableRestaurant entity);
}
