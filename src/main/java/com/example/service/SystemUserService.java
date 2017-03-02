package com.example.service;

import com.example.model.SystemUser;

public interface SystemUserService {
	
	public String getType(String email);
	
	public SystemUser findOne(Long id);
	
}
