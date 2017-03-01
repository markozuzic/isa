package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.SystemUserRepository;

@Service
public class SystemUserServiceImp implements SystemUserService {
	
	@Autowired
	private SystemUserRepository systemUserRepository;

	@Override
	public String getType(String email) {
		if (systemUserRepository.findByEmail(email).isEmpty()) {
			return "";
		}
		return systemUserRepository.findByEmail(email).get(0).getType();
	}

}
