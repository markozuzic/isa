package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.model.User;
import com.example.model.Waiter;
import com.example.service.BartenderService;

@RestController
public class BartenderController {

	@Autowired
	private BartenderService bartenderService;
	
	@RequestMapping(
			value = "/bartender/register",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createBartender(@RequestBody Bartender bartender){
		return bartenderService.createBartender(bartender);
	}
	
	@RequestMapping(
			value = "/bartender/updateProfile",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String updateBartenderProfile(@RequestBody Bartender bartender){
		String response = bartenderService.updateBartenderProfile(bartender);
		return response;
	}
	
	@RequestMapping(
			value = "/bartender/updatePassword",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String updateBartenderPassword(@RequestBody Bartender bartender){
		String response = bartenderService.updatePassword(bartender);
		return response;
	}
	
	@RequestMapping(
			value = "/bartender/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String logInBartender(@RequestBody Bartender bartender){
		return bartenderService.logInBartender(bartender);
	}
}
