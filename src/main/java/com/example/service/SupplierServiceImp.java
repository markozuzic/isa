package com.example.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.model.Demand;
import com.example.model.Offer;
import com.example.model.Supplier;
import com.example.model.SystemUser;
import com.example.repository.DemandRepository;
import com.example.repository.OfferRepository;
import com.example.repository.SupplierRepository;
import com.example.repository.SystemUserRepository;

@Service
public class SupplierServiceImp implements SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired 
	private SystemUserRepository systemUserRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private DemandRepository demandRepository;

	@Override
	public Supplier createSupplier(Supplier newSupplier) {
		if (systemUserRepository.findByEmail(newSupplier.getEmail()).isEmpty()) {
			supplierRepository.save(newSupplier);
			SystemUser su = new SystemUser(newSupplier.getEmail(), newSupplier.getPassword(), "supplier");
			systemUserRepository.save(su);
			return newSupplier;
		}
		newSupplier.setId(0);
		return newSupplier;
	}

	@Override
	public List<Offer> getAllOffers() {
		Supplier supplier = (Supplier) httpSession.getAttribute("supplier");
		return supplierRepository.findOne(supplier.getId()).getOffers();
	}

	@Override
	public Supplier getSupplier() {
		Supplier su = (Supplier) httpSession.getAttribute("supplier");
		su.setOffers(offerRepository.findBySupplierId(su.getId()));
		return su;
	}

	@Override
	public String modifySupplier(Supplier supplier) {
		List<Supplier> suppliers = supplierRepository.findByEmail(supplier.getEmail());
		List<SystemUser> users = systemUserRepository.findByEmail(supplier.getEmail());
		
		if (!users.isEmpty() && (suppliers.get(0).getId() != supplier.getId())) {
			return "Email error";
		} 
		
		supplierRepository.save(supplier);
		return "OK";
	}

	@Override
	public Page<Supplier> getSuppliers() {
		return supplierRepository.findAll(null);
	}

	@Override
	public String logIn(String email, String password) {
		List<Supplier> suppliers = supplierRepository.findByEmail(email);
		if (suppliers.isEmpty()) {
			return "EmailError";
		}
		
		Supplier s = suppliers.get(0);
		if (s.getPassword().equals(password)){
			httpSession.setAttribute("supplier", s);
			return "supplier";
		}
		else {
			return "PasswordError";
		}
		
	}

	@Override
	public List<Demand> getActiveDemands() {
		return demandRepository.findByIsActive(true);
	}

	@Override
	public String createOrUpdate(Offer offer) {
		Supplier supplier = (Supplier) httpSession.getAttribute("supplier");
		List<Offer> offers = offerRepository.findBySupplierIdAndDemandId(supplier.getId(), offer.getDemandId());
		if(offers.isEmpty()) {
			Offer o = new Offer(new Date(), supplier.getId(), offer.getDemandId(), offer.getPrice());
			offerRepository.save(o);
		}
		else {
			Offer updatedOffer = offers.get(0);
			updatedOffer.setPrice(offer.getPrice());
			offerRepository.save(updatedOffer);
		}
		return "OK";
	}

}
