package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import com.example.model.User;

public interface UserRepository extends Repository<User, Long>{
		
	public User save(User entity);
	
	public List<User> findByEmail(String email);
	
	public User findOne(long id);
	
	public Page<User> findAll(Pageable pageable);
}
