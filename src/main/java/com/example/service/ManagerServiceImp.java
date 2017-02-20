package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Manager;
import com.example.repository.ManagerRepository;

@Service
public class ManagerServiceImp implements ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private HttpSession httpSession;
	
	@Override
	public void createManager(Manager newManager) {
		managerRepository.save(newManager);
	}

	@Override
	public String logInManager(Manager logger) {
		List<Manager> managers = managerRepository.findByEmail(logger.getEmail());
		if (managers.isEmpty()) {
			return "Email error";
		}
		else {
			Manager manager = managers.get(0);
			if (manager.getPassword().equals(logger.getPassword())) {
				httpSession.setAttribute("user", manager);
				return "OK";
			}
			else {
				return "Password error";
			}
		}
	}

}
