package com.example.service;

import java.util.List;

import com.example.model.Offer;

public interface OfferService {

	public String createOffer(Offer newOffer);

	public List<Offer> getOfferForDemands();

	public String approveOffer(String id);
	
}
