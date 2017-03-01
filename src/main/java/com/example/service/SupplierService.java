package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.Offer;
import com.example.model.Supplier;

public interface SupplierService {

	public Supplier createSupplier(Supplier newSupplier);
	
	public List<Offer> getAllOffers(String email);
	
	public Supplier getSupplier(String email);
	
	public String modifySupplier(Supplier supplier);

	public Page<Supplier> getSuppliers();
	
	public String logIn(String email, String password);
	
}
