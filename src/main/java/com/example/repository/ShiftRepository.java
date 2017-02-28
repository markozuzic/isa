package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.Shift;

public interface ShiftRepository extends Repository<Shift, Long> {
	
	public Shift save(Shift shift);
	
	public Shift findOne(long id);
	
	public Page<Shift> findAll(Pageable pageable);
	
	public List<Shift> findByEmployeeType(String employeeType);
	
	

}
