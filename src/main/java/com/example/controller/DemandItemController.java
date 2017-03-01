package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.DemandItem;
import com.example.service.DemandItemService;

@RestController
public class DemandItemController {
	
	@Autowired
	private DemandItemService demandItemService;
	
	@RequestMapping(
			value = "/demandItem/create",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public DemandItem create(@RequestBody DemandItem newDemandItem) {
		return demandItemService.create(newDemandItem);
	}
}
