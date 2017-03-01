package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.Supplier;

public interface SupplierRepository extends Repository<Supplier, Long> {
	
	public Supplier save(Supplier entity);
	
	public Supplier findOne(long id);
	
	public List<Supplier> findByEmail(String email);

	public Page<Supplier> findAll(Pageable pageable);

}
