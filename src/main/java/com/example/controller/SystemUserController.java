package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.SystemUser;
import com.example.service.ManagerService;
import com.example.service.SupplierService;
import com.example.service.SystemUserService;
import com.example.service.UserService;

@RestController
public class SystemUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(
			value = "/systemUser/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String login(@RequestBody SystemUser systemUser) {
		String email = systemUser.getEmail();
		String password = systemUser.getPassword();
		String type = systemUserService.getType(email);
		if (type.equals("user")) {
			return userService.logInUser(email, password);
		}
		else if (type.equals("restaurant") || type.equals("system")) {
			return managerService.logInManager(email, password);
		}
		else if (type.equals("supplier")) {
			return supplierService.logIn(email, password);
		}
		else {
			return "EmailError";
		}
	}
}
