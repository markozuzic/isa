package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.DemandItem;

public interface DemandItemRepository extends Repository<DemandItem, Long> {
	
	public DemandItem save(DemandItem entity);
	
	public DemandItem findOne(long id);
	
	public List<DemandItem> findByName(String name);

}
