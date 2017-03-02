package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.Demand;
import com.example.model.Offer;
import com.example.model.Supplier;
import com.example.model.SystemUser;

public interface SupplierService {

	public Supplier createSupplier(Supplier newSupplier);
	
	public List<Offer> getAllOffers();
	
	public Supplier getSupplier();
	
	public String modifySupplier(Supplier supplier);

	public Page<Supplier> getSuppliers();
	
	public String logIn(String email, String password);

	public List<Demand> getActiveDemands();

	public String createOrUpdate(Offer offer);
	
	public String firstLogin(SystemUser systemUser);
}
