package com.example.repository;

import com.example.model.Shift;
import org.springframework.data.repository.Repository;

public interface ShiftRepository extends Repository<Shift, Long> {
	
	public Shift save(Shift shift);
	
	public Shift findOne(long id);

}
