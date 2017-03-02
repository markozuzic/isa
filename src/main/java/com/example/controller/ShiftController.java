package com.example.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Shift;
import com.example.service.ShiftService;

@RestController
public class ShiftController {
	
	@Autowired
	private ShiftService shiftService;
	
	@RequestMapping(
			value = "/shift/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createShift(@RequestBody Shift newShift) {
		return shiftService.createShift(newShift);
	}
	
	@RequestMapping(
			value = "/shift/getAllShifts",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Collection<Shift> getAllShifts(){
		return shiftService.getAllShifts();
	}
	
	@RequestMapping(
			value = "/shift/createWaiterShift/{tableNumbers}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createWaiterShift(@PathVariable("tableNumbers") String tableNumbers, 
									@RequestBody Shift newShift) {
		return shiftService.createWaiterShift(tableNumbers, newShift);
	}
	
	@RequestMapping(
			value = "/shift/createBartenderShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createBartenderShift(@RequestBody Shift newShift) {
		return shiftService.createBartenderShift(newShift);
	}
	
	@RequestMapping(
			value = "/shift/createChefShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createChefShift(@RequestBody Shift newShift) {
		return shiftService.createChefShift(newShift);
	}
	
}
