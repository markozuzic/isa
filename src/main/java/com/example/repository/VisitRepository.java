package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.Visit;

public interface VisitRepository extends Repository<Visit, Long>{
	
	public Visit save(Visit entity);
	
	public Page<Visit> findAll(Pageable pageable);
	
	public List<Visit> findByUserId(long userId);
}
