package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Demand;
import com.example.model.Manager;
import com.example.model.SystemUser;
import com.example.repository.DemandRepository;
import com.example.repository.ManagerRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.SystemUserRepository;

@Service
public class ManagerServiceImp implements ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	@Autowired
	private DemandRepository demandRepository;
	
	@Override
	public String createManager(Manager newManager, Long restaurantId) {
		if (systemUserRepository.findByEmail(newManager.getEmail()).isEmpty()) {
			newManager.setRestaurantId(restaurantId);
			managerRepository.save(newManager);
			SystemUser s = new SystemUser(newManager.getEmail(), newManager.getPassword(), newManager.getType());
			systemUserRepository.save(s);
			return "OK";
		}
		return "Id error";
	}
	
	@Override
	public String createManager(Manager newManager) {
		if (systemUserRepository.findByEmail(newManager.getEmail()).isEmpty()) {
			if (newManager.getType().equals("system")) {
				newManager.setRestaurantId(0);
			}
			managerRepository.save(newManager);
			SystemUser s = new SystemUser(newManager.getEmail(), newManager.getPassword(), newManager.getType());
			systemUserRepository.save(s);
			return "OK";
		}
		return "Id error";
	}

	@Override
	public String logInManager(String email, String password) {
		List<Manager> managers = managerRepository.findByEmail(email);
		if (managers.isEmpty()) {
			return "Email error";
		}
		else {
			Manager manager = managers.get(0);
			if (manager.getPassword().equals(password)) {
				httpSession.setAttribute("manager", manager);
				return manager.getType();
			}
			else {
				return "PasswordError";
			}
		}
	}

	@Override
	public Manager getLoggedIn() {
		return (Manager) httpSession.getAttribute("manager");
	}

	@Override
	public List<Demand> getMyDemands() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		return demandRepository.findByRestaurantId(m.getRestaurantId());
	}

}
