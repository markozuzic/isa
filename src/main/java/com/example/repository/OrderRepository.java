package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import com.example.model.MenuItem;
import com.example.model.OrderR;
import com.example.model.Shift;
import com.example.model.User;
import com.example.model.Visit;

public interface OrderRepository extends Repository<OrderR, Long> {

	public OrderR save(OrderR entity);
	
	public void delete(OrderR entity);
	
	public List<OrderR> findById(long Id);
	
	public OrderR findOne(long id);
	
	public List<OrderR> findByVisit(Visit visit);

	public Page<OrderR> findAll(Pageable pageable);
	
}
