package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.Manager;

public interface ManagerRepository extends Repository<Manager, Long> {
	
	public Manager save(Manager entity);
	public List<Manager> findByEmail(String email);
	public Manager findOne(long id);
}
