package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Bidder;
import com.example.repository.BidderRepository;

@Service
public class BidderServiceImp implements BidderService {
	
	@Autowired
	private BidderRepository bidderRepository;

	@Override
	public String createBidder(Bidder newBidder) {
		if (bidderRepository.findOne(newBidder.getId()) == null) {
			bidderRepository.save(newBidder);
			return "OK";
		}
		return "Id error";
	}

}
