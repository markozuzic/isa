package com.example.service;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Chef;
import com.example.model.Manager;
import com.example.model.Restaurant;
import com.example.repository.ChefRepository;
import com.example.repository.RestaurantRepository;

@Service
public class ChefServiceImpl implements ChefService {

	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private ChefRepository chefRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Override
	public Chef createChef(Chef newChef) {
		
		if(chefRepository.findById(newChef.getId()).isEmpty()){
			Manager m = (Manager) httpSession.getAttribute("manager");
			newChef.setRestaurantId(m.getRestaurantId());	
			return chefRepository.save(newChef);
		}
		else {
			newChef.setId(0);
			return newChef;
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
	public List<Chef> getAllChefs() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getId());
		
		return chefRepository.findByRestaurantId(r.getId());
	}

	public String logInChef(Chef chef) {
		return null;
	}
	
}
