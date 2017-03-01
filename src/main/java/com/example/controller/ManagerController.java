package com.example.controller;

import javax.websocket.server.PathParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Manager;
import com.example.service.ManagerService;

@RestController
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(
			value = "/manager/register/{restaurantId}",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String registerManager(@RequestBody Manager manager, @PathVariable("restaurantId") Long id) {
		manager.setType("restaurant");
		return managerService.createManager(manager, id);
	}
	
	@RequestMapping(
			value = "/systemManager/register",
			method = RequestMethod.POST,
			consumes =  MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String registerManager(@RequestBody Manager manager) {
		manager.setType("system");
		return managerService.createManager(manager);
	}
	
	@RequestMapping(
			value = "/manager/getLoggedIn",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Manager getLoggedIn() {
		return managerService.getLoggedIn();
	}
	
	
}
