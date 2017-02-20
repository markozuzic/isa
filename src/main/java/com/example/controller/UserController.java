package com.example.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.model.Visit;
import com.example.service.UserService;

@Transactional
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
		return userService.createUser(user);
	}
	
	@RequestMapping(
			value = "/user/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String logInUser(@RequestBody User user){
		return userService.logInUser(user);
	}
	
	@RequestMapping(
			value = "/user/activateAccount",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String activateAccount(@RequestBody User user){
		return userService.activateAccount(user);
	}
	
	
	@RequestMapping(
			value = "/user/getLoggedInUser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getLoggedInUser(){
		return new ResponseEntity<User>(userService.getLoggedInUser(), HttpStatus.OK);
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
   
   @RequestMapping(
			value = "/user/getAllUsers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<User> getAllUsers(){
	    return userService.getAllUsers().getContent();
	}
   
    @RequestMapping(
    		value = "/user/addFriend/{id}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFriend(@PathVariable("id") Long id){
    	return new ResponseEntity<String>(userService.addFriend(id), HttpStatus.OK);
    }
    
    @RequestMapping(
    		value = "/user/removeFriend/{id}",
    		method = RequestMethod.GET,
    		produces = MediaType.TEXT_PLAIN_VALUE)
    public String removeFriend(@PathVariable("id") Long id){
    	return userService.removeFriend(id);
    }
    
    @RequestMapping(
			value = "/user/getPendingRequests",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<User> getPendingRequestsForUser(){
	    return userService.getPendingRequestsForUser();
    }

    @RequestMapping(
    		value = "/user/acceptFriendRequest/{id}",
    		method = RequestMethod.GET,
    		produces = MediaType.TEXT_PLAIN_VALUE)
    public String acceptFriendRequest(@PathVariable("id") Long id){
    	return userService.acceptFriendRequest(id);
    }
    
    @RequestMapping(
    		value = "/user/denyFriendRequest/{id}",
    		method = RequestMethod.GET,
    		produces = MediaType.TEXT_PLAIN_VALUE)
    public String denyFriendRequest(@PathVariable("id") Long id){
    	return userService.denyFriendRequest(id);
    }
    
    @RequestMapping(
			value = "/user/getFriends",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<User> getFriendsForUser(){
	    return userService.getFriendsForUser();
    }
    
    @RequestMapping(
			value = "/user/getFriendSuggestions",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<User> getFriendSuggestions(){
	    return userService.getFriendSuggestions();
    }
    
    @RequestMapping(
    		value = "/user/getVisits",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Visit> getVisits(){
    	return userService.getVisitsForUser();
    }
    
}
