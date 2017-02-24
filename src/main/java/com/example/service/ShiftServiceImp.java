package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Shift;
import com.example.repository.ShiftRepository;

@Service
public class ShiftServiceImp implements ShiftService {
	
	@Autowired
	private ShiftRepository shiftRepository;

	@Override
	public String createShift(Shift newShift) {
		if (shiftRepository.findOne(newShift.getId()) == null) {
			shiftRepository.save(newShift);
			return "OK";
		}
		return "Id error";
	}

}
