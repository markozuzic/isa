package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Manager;
import com.example.repository.ManagerRepository;
import com.example.service.ManagerService;

public class ManagerController {
	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(
			value = "/manager/register",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String registerManager(@RequestBody Manager manager) {
		managerService.createManager(manager);
		return "OK";
	}
	
	@RequestMapping(
			value = "/manager/login",
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String logInManager(@RequestBody Manager manager) {
		String response = managerService.logInManager(manager);
		return response;
	}
	
	
}
