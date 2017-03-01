package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.OrderR;
import com.example.model.Visit;
import com.example.model.Waiter;

public interface OrderRepository extends Repository<OrderR, Long> {

	public OrderR save(OrderR entity);
	
	public void delete(OrderR entity);
	
	public List<OrderR> findById(long Id);
	
	public OrderR findOne(long id);
	
	public List<OrderR> findByVisit(Visit visit);

	public Page<OrderR> findAll(Pageable pageable);
	
	public List<OrderR>	findByWaiter(Waiter w);
	
}
