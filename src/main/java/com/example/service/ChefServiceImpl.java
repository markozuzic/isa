package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Chef;
import com.example.model.User;
import com.example.repository.ChefRepository;

@Service
public class ChefServiceImpl implements ChefService {

	@Autowired
	HttpSession httpSession;
	
	@Autowired
	ChefRepository chefRepository;
	
	@Override
	public String createChef(Chef newChef) {
		if(chefRepository.findById(newChef.getId()).isEmpty()){
			chefRepository.save(newChef);
			return "OK";
		}
		else{
			return "IdError";
		}
	}

	@Override
	public String updateChefProfile(Chef chef) {
		Chef oldChef = chefRepository.findOne(chef.getId());
		oldChef.setBirthDate(chef.getBirthDate());
		oldChef.setClothesSize(chef.getClothesSize());
		oldChef.setLastname(chef.getLastname());
		oldChef.setName(chef.getName());
		oldChef.setShoeSize(chef.getShoeSize());
		
		chefRepository.save(oldChef);
		httpSession.setAttribute("chef", oldChef);
		
		return "OK";
	}

	@Override
	public String updatePassword(Chef chef) {
		Chef oldChef = chefRepository.findOne(chef.getId());
		oldChef.setPassword(chef.getPassword());
		
		chefRepository.save(oldChef);
		httpSession.setAttribute("chef", oldChef);
		
		return "OK";
	}

	@Override
	public String logInChef(Chef chef) {
		return null;
	
	}
	
	
	

}
