package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Demand;
import com.example.model.Offer;
import com.example.model.Supplier;
import com.example.service.SupplierService;

@RestController
public class SupplierController {

	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping(
				value = "/supplier/create",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Supplier createSupplier(@RequestBody Supplier newSupplier) {
		return supplierService.createSupplier(newSupplier);
	}
	
	@RequestMapping(
				value = "/supplier/getAllOffers",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<Offer> getAllOffers() {
		return supplierService.getAllOffers();
	}
	
	@RequestMapping(
				value = "/supplier/getSupplier",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Supplier getSupplier() {
		return supplierService.getSupplier();
	}
	
	@RequestMapping(
				value = "/supplier/modifySypplier",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String modifySupplier(@RequestBody Supplier supplier) {
		return supplierService.modifySupplier(supplier);
	}
	
	@RequestMapping(
			value = "/supplier/getSuppliers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	public List<Supplier> getSuppliers() {
		return supplierService.getSuppliers().getContent();
	}
	
	@RequestMapping(
			value = "/supplier/getActiveDemands",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	public List<Demand> getActiveDemands() {
		return supplierService.getActiveDemands();
	}
	
	@RequestMapping(
			value = "/supplier/createOrUpdate",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
		)
	public String createOrUpdate(@RequestBody Offer offer) {
		return supplierService.createOrUpdate(offer);
	}
		
}
