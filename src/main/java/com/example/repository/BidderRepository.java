package com.example.repository;

import org.springframework.data.repository.Repository;

import com.example.model.Bidder;

public interface BidderRepository extends Repository<Bidder, Long> {
	
	public Bidder save(Bidder entity);
	
	public Bidder findOne(long id);

}
