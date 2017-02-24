package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MenuItem;
import com.example.service.MenuItemService;

@RestController
public class MenuItemController {
	
	@Autowired
	private MenuItemService menuItemService;
	
	@RequestMapping(
				value = "/menuItem/create",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createMenuItem(@RequestBody MenuItem newMenuItem) {
		return menuItemService.createMenuItem(newMenuItem);
	}
		
}
