package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.MenuItem;

public interface MenuItemRepository extends Repository<MenuItem, Long> {
	
	public MenuItem save(MenuItem enity);
	
	public MenuItem findOne(long id);
	
	public List<MenuItem> findByType(String type);
}
