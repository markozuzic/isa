package com.example.repository;

import org.springframework.data.repository.Repository;

import com.example.model.Offer;

public interface OfferRepository extends Repository<Offer, Long> {
	
	public Offer save(Offer entity);
	
	public Offer findOne(long id);
	
}
