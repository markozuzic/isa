package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Bidder;
import com.example.service.BidderService;

@RestController
public class BidderController {

	@Autowired
	private BidderService bidderService;
	
	@RequestMapping(
				value = "bidder/create",
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.TEXT_PLAIN_VALUE
			)
	public String createBidder(Bidder newBidder) {
		return bidderService.createBidder(newBidder);
	}
	
}
