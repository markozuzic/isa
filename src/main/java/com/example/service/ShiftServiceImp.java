package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.model.Shift;
import com.example.model.User;
import com.example.model.Waiter;
import com.example.repository.ShiftRepository;

@Service
public class ShiftServiceImp implements ShiftService {
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Autowired
	private HttpSession httpSession;

	@Override
	public String createShift(Shift newShift) {
		shiftRepository.save(newShift);
		return "OK";
	}

	//ODRADI OVO
	@Override
	public List<Shift> getAllShifts() {
		//Waiter waiter = (Waiter) httpSession.getAttribute("waiter");
		//Chef chef = (Chef) httpSession.getAttribute("chef");
		//Bartender bartender = (Bartender) httpSession.getAttribute("bartender");
		
		/*if(waiter!=null){
			return shiftRepository.findByEmployeeType("waiter");
		}
		else if(chef!=null){
			return shiftRepository.findByEmployeeType("chef");
		}
		else if(bartender!=null){
			return shiftRepository.findByEmployeeType("bartender");
		}
		else{
			return null;
		}*/

		String employeeType = "";
		long restaurantId = 0;
		Waiter waiter = (Waiter) httpSession.getAttribute("waiter");
		if(waiter != null) {
			employeeType = "waiter";
			restaurantId = waiter.getRestaurantId();
		} else {
			Chef chef = (Chef) httpSession.getAttribute("chef");
			if(chef != null) {
				employeeType = "chef";
				restaurantId = chef.getRestaurantId();
			} else {
				Bartender bartender = (Bartender) httpSession.getAttribute("bartender");
				if (bartender !=null){
					employeeType = "bartender";
					restaurantId = bartender.getRestaurantId();
				}
			}
		}
		
		return shiftRepository.findByEmployeeTypeAndRestaurantId(employeeType, restaurantId);
	}
	
}
