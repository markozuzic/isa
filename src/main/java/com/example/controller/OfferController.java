package com.example.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Offer;
import com.example.service.OfferService;

@RestController
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@RequestMapping(
			value = "/offer/create",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createOffer(@RequestBody Offer newOffer) {
		return offerService.createOffer(newOffer);
	}
	
	@RequestMapping(
			value = "/offer/getOfferForDemands",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Collection<Offer> getOfferForDemands() {
		return offerService.getOfferForDemands();
	}
	
	@RequestMapping(
			value = "/offer/approveOffer",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String approveOffer(@RequestBody String id) {
		return offerService.approveOffer(id);
	}
}
