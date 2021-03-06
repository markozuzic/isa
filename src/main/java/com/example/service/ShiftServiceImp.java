package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.model.Shift;
import com.example.model.TableRestaurant;
import com.example.model.Waiter;
import com.example.repository.ShiftRepository;
import com.example.repository.TableRepository;

@Service
public class ShiftServiceImp implements ShiftService {
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private TableRepository tableRepository;

	@Override
	public String createShift(Shift newShift) {
		shiftRepository.save(newShift);
		return "OK";
	}

	//ODRADI OVO
	@Override
	public List<Shift> getAllShifts() {
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

	@Override
	public String createWaiterShift(String tableNumbers, Shift newShift) {
		if (newShift.getEmployeeId() == 0) {
			return "NoIdError";
		}
		List<Shift> definedShifts = shiftRepository.findByEmployeeId(newShift.getEmployeeId());
		if (!definedShifts.isEmpty()) {
			for (Shift shift : definedShifts) {
				if (!newShift.getDate().after(shift.getDate())
					&& !newShift.getDate().before(shift.getDate())) {
					if (shift.getEmployeeType().equals("waiter")) {
						return "IdError";
					}
				}
			}
		}
		String[] tables = tableNumbers.split(",");
		ArrayList<TableRestaurant> tableList = new ArrayList<TableRestaurant>();
		for (String tableNumber : tables) {
			tableList.add(tableRepository.findOne(Long.parseLong(tableNumber)));
		}
		newShift.setReon(tableList);
		newShift.setEmployeeType("waiter");
		shiftRepository.save(newShift);
		return "OK";
	}

	@Override
	public String createBartenderShift(Shift newShift) {
		if (newShift.getEmployeeId() == 0) {
			return "NoIdError";
		}
		List<Shift> definedShifts = shiftRepository.findByEmployeeId(newShift.getEmployeeId());
		if (!definedShifts.isEmpty()) {
			for (Shift shift : definedShifts) {
				if (!newShift.getDate().after(shift.getDate())
					&& !newShift.getDate().before(shift.getDate())) {
					if (shift.getEmployeeType().equals(newShift.getEmployeeType())) {
						return "IdError";
					}
				}
			}
		}
		newShift.setEmployeeType("bartender");
		shiftRepository.save(newShift);
		return "OK";
	}

	@Override
	public String createChefShift(Shift newShift) {
		if (newShift.getEmployeeId() == 0) {
			return "NoIdError";
		}
		List<Shift> definedShifts = shiftRepository.findByEmployeeId(newShift.getEmployeeId());
		if (!definedShifts.isEmpty()) {
			for (Shift shift : definedShifts) {
				if (!newShift.getDate().after(shift.getDate())
					&& !newShift.getDate().before(shift.getDate())) {
					if (shift.getEmployeeType().equals(newShift.getEmployeeType())) {
						return "IdError";
					}
				}
			}
		}
		newShift.setEmployeeType("chef");
		shiftRepository.save(newShift);
		return "OK";
	}


	
}
