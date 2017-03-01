package com.example.service;

import java.util.List;

import com.example.model.TableRestaurant;

public interface TableService {
	
	public String createTable(TableRestaurant newTable);
	
	public List<TableRestaurant> findByRestaurant(long id);
	
	public List<TableRestaurant> findByRestaurant();

	public String deleteTable(long id);

}
