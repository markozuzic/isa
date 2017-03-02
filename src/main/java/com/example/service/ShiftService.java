package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.Shift;

public interface ShiftService {

	public String createShift(Shift newShift);
	
	public List<Shift> getAllShifts();

	public String createWaiterShift(String tableNumbers, Shift newShift);

	public String createBartenderShift(Shift newShift);

	public String createChefShift(Shift newShift);
		
}
