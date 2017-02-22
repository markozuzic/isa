package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import com.example.model.TableRestaurant;

public interface TableRepository extends Repository<TableRestaurant, Long> {
	
	public TableRestaurant save(TableRestaurant entity);
	
	public TableRestaurant findOne(long id);
	
	public List<TableRestaurant> findByRestaurant(long restaurant);
}
