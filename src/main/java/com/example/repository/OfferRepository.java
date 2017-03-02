package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Offer;
import com.example.model.SystemUser;

public interface OfferRepository extends Repository<Offer, Long> {
	
	public Offer save(Offer entity);
	
	public Offer findOne(long id);
	
	public List<Offer> findBySupplierId(long id);
	
	public List<Offer> findByDemandId(long id);

	public List<Offer> findBySupplierIdAndDemandId(long id, long demandId);
	
}
