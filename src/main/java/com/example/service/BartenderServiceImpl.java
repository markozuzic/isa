package com.example.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.repository.BartenderRepository;

@Service
public class BartenderServiceImpl implements BartenderService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private BartenderRepository bartenderRepository;
	
	@Override
	public String createBartender(Bartender newBartender) {
		if(bartenderRepository.findById(newBartender.getId()).isEmpty()){
			bartenderRepository.save(newBartender);
			return "OK";
		}
		else{
			return "IdError";
		}
	}

	@Override
	public String updateBartenderProfile(Bartender bartender) {
		Bartender oldBartender = bartenderRepository.findOne(bartender.getId());
		oldBartender.setBirthDate(bartender.getBirthDate());
		oldBartender.setClothesSize(bartender.getClothesSize());
		oldBartender.setLastname(bartender.getLastname());
		oldBartender.setName(bartender.getName());
		oldBartender.setShoeSize(bartender.getShoeSize());
		
		bartenderRepository.save(oldBartender);
		httpSession.setAttribute("bartender", oldBartender);
		
		return "OK";
	}

	@Override
	public String updatePassword(Bartender bartender) {
		Bartender oldBartender = bartenderRepository.findOne(bartender.getId());
		oldBartender.setPassword(bartender.getPassword());
		
		bartenderRepository.save(oldBartender);
		httpSession.setAttribute("bartender", oldBartender);
		
		return "OK";
	}

	@Override
	public String logInBartender(Bartender bartender) {
		// TODO Auto-generated method stub
		return null;
	}

}
