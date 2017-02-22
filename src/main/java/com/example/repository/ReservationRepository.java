package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.Reservation;

public interface ReservationRepository extends Repository<Reservation, Long> {

	public Reservation save(Reservation entity);
	
	public Page<Reservation> findAll(Pageable pageable);
	
	public Reservation findOne(long id);
	
}
