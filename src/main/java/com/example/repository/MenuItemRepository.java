package com.example.repository;

import org.springframework.data.repository.Repository;

import com.example.model.MenuItem;

public interface MenuItemRepository extends Repository<MenuItem, Long> {
	
	public MenuItem save(MenuItem entity);
	
	public MenuItem findOne(Long id);
	
	public void delete(MenuItem entity);
	
}
