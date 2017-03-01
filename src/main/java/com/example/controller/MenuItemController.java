package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
				value = "/menuItem/createMenuItem",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public MenuItem createMenuItem(@RequestBody MenuItem newMenuItem) {
		return menuItemService.createMenuItem(newMenuItem);
	}
	
	@RequestMapping(
			value = "/menuItem/createDrinkItem",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	public MenuItem createDrinkItem(@RequestBody MenuItem newMenuItem) {
		return menuItemService.createDrinkItem(newMenuItem);
	}
	
	@RequestMapping(
			value = "/menuItem/removeDrinkItem/{id}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String removeDrinkItem(@PathVariable("id") Long id) {
		return menuItemService.removeDrinkItem(id);
	}
	
	@RequestMapping(
			value = "/menuItem/removeMenuItem/{id}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String removeMenuItem(@PathVariable("id") Long id) {
		return menuItemService.removeMenuItem(id);
	}
		
}
