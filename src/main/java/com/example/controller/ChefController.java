package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.Chef;
import com.example.service.ChefService;

@RestController
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@RequestMapping(
			value = "/chef/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	private Chef CreateChef(@RequestBody Chef chef){
		return chefService.createChef(chef);
	}
	
	@RequestMapping(
			value = "/chef/updateProfile",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String updateChefProfile(@RequestBody Chef chef){
		String response = chefService.updateChefProfile(chef);
		return response;
	}
	
	@RequestMapping(
			value = "/chef/updatePassword",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String updateChefPassword(@RequestBody Chef chef){
		String response = chefService.updatePassword(chef);
		return response;
	}
	
	@RequestMapping(
			value = "/chef/getChefs",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<Chef> getAllChefs() {
		return chefService.getAllChefs();
	}
	
}
