package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Bartender;
import com.example.model.Manager;
import com.example.model.Restaurant;
import com.example.repository.BartenderRepository;
import com.example.repository.RestaurantRepository;

@Service
public class BartenderServiceImpl implements BartenderService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private BartenderRepository bartenderRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public Bartender createBartender(Bartender newBartender) {
		
		if(bartenderRepository.findById(newBartender.getId()).isEmpty()){
			Manager m = (Manager) httpSession.getAttribute("manager");
			newBartender.setRestaurantId(m.getRestaurantId());
			return bartenderRepository.save(newBartender);
		}
		else {
			newBartender.setId(0);
			return newBartender;
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
	public List<Bartender> getAllBartenders() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getRestaurantId());
		
		return bartenderRepository.findByRestaurantId(r.getId());
	}

	public String logInBartender(Bartender bartender) {
		// TODO Auto-generated method stub
		return null;
	}

}
