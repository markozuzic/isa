package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TableRestaurant;
import com.example.service.TableService;

@RestController
public class TableController {
	
	@Autowired
	private TableService tableService;
	
	@RequestMapping(
				value = "/table/create",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createTable(@RequestBody TableRestaurant newTable) {
		return tableService.createTable(newTable);
	}
	
	@RequestMapping(
			value = "/table/getTables/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<TableRestaurant> getTables(@PathVariable("id") long id) {
		return tableService.findByRestaurant(id);
	}
	
}
