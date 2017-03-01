package com.example.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.model.SystemUser;

public interface SystemUserRepository extends Repository<SystemUser, Long> {

	public SystemUser save(SystemUser entity);
	
	public List<SystemUser> findByEmail(String email);
	
}
