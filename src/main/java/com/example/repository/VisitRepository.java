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
	
	public List<Visit> findByUserIdAndReservationId(long userId, long reservationId);
	
	public List<Visit> findByReservationId(long reservationId);
	
	public void delete(Visit entity);
	
	public Visit findOne(long id);
}
