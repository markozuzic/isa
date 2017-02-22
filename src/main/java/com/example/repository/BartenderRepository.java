package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Bartender;

public interface BartenderRepository extends Repository<Bartender, Long> {

	public Bartender save(Bartender entity);
	
	public void delete(Bartender entity);
	
	public Bartender findOne(Long id);
	
	public List<Bartender> findById(Long id);
	
}
