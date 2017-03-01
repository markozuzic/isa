package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Demand;
import com.example.service.DemandService;

@RestController
public class DemandController {
	
	@Autowired
	private DemandService demandService;
	
	@RequestMapping(
			value = "/demand/getDemands",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<Demand> getDemands() {
		return demandService.getAllDemands();
	}
	
	@RequestMapping(
			value = "/demand/createDemand/{idList}",
			method = RequestMethod.POST,
			produces = MediaType.TEXT_PLAIN_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public String create(@PathVariable("idList") String idList, @RequestBody Demand d) {
		return demandService.create(idList, d);
	}
}
