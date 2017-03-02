package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Bartender;
import com.example.model.Chef;
import com.example.model.Manager;
import com.example.model.Restaurant;
import com.example.model.SystemUser;
import com.example.model.Waiter;
import com.example.repository.BartenderRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.SystemUserRepository;

@Service
public class BartenderServiceImpl implements BartenderService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private BartenderRepository bartenderRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	@Override
	public Bartender createBartender(Bartender newBartender) {
		
		if(systemUserRepository.findByEmail(newBartender.getEmail()).isEmpty()){
			Manager m = (Manager) httpSession.getAttribute("manager");
			newBartender.setRestaurantId(m.getRestaurantId());
			SystemUser su = new SystemUser(newBartender.getEmail(), newBartender.getPassword(), "bartender");
			systemUserRepository.save(su);
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

	@Override
	public String logInBartender(String email, String password) {
		List<Bartender> bartenders = bartenderRepository.findByEmail(email);
		if (bartenders.isEmpty()) {
			return "EmailError";
		}
		else {
			Bartender bartender = bartenders.get(0);
			if(bartender.isFirstLogin()) {
				List<SystemUser> systemUser = systemUserRepository.findByEmail(email);
				long id = systemUser.get(0).getId();
				return "firstLogin,"+id;
			}
			if (bartender.getPassword().equals(password)) {
				httpSession.setAttribute("bartender", bartender);
				return "bartender";
			}
			else {
				return "PasswordError";
			}
		}
	}

	@Override
	public String firstLogin(SystemUser systemUser) {
		Bartender bartender = bartenderRepository.findByEmail(systemUser.getEmail()).get(0);
		bartender.setPassword(systemUser.getPassword());
		bartender.setFirstLogin(false);
		bartenderRepository.save(bartender);
		systemUserRepository.save(systemUser);
		return "OK";
	}

	@Override
	public Bartender getLoggedIn() {
		return (Bartender) httpSession.getAttribute("bartender");
	}

}
