package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(
			value = "/user/register",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String registerUser(@RequestBody User user){
		userService.createUser(user);
		return "OK";
	}
	
	@RequestMapping(
			value = "/user/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String logInUser(@RequestBody User user){
		String response = userService.logInUser(user);
		return response;
	}
	
	@RequestMapping(
			value = "/user/getLoggedInUser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getLoggedInUser(){
		return userService.getLoggedInUser();
	}
	
   @RequestMapping(
			value = "/user/updateUserInfo",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateUserInfo(@RequestBody User user){
	   	String response = userService.updateUserInfo(user);
		return response;
	}
   
   @RequestMapping(
			value = "/user/updatePassword",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updatePassword(@RequestBody User user){
	   	String response = userService.updatePassword(user);
		return response;
	}
}
