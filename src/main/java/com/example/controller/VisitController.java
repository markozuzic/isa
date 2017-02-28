package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.VisitService;

@RestController
public class VisitController {

	@Autowired
	VisitService visitService;
	
	@RequestMapping(
			value = "/visit/rateRestaurant/{visitid}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String rateRestaurant(@PathVariable("visitid") Long visitId, @RequestBody String rating) {
		return visitService.rateRestaurant(visitId, rating);
	}
	
	@RequestMapping(
			value = "/visit/rateMeal/{visitid}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String rateMeal(@PathVariable("visitid") Long visitId, @RequestBody String rating) {
		return visitService.rateMeal(visitId, rating);
	}
	
	@RequestMapping(
			value = "/visit/rateService/{visitid}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String rateService(@PathVariable("visitid") Long visitId, @RequestBody String rating) {
		return visitService.rateService(visitId, rating);
	}
}
