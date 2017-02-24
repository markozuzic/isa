package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Offer;
import com.example.repository.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository offerRepository;

	@Override
	public String createOffer(Offer newOffer) {
		if (offerRepository.findOne(newOffer.getId()) == null) {
			offerRepository.save(newOffer);
			return "OK";
		}
		return "Id error";
	}

}
