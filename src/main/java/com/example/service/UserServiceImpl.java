package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Override
	public void createUser(User newUser) {
		userRepository.save(newUser);
	}

	@Override
	public String logInUser(User logger) {
		List<User> users = userRepository.findByEmail(logger.getEmail());
		if(users.isEmpty()) {
			return "EmailError";
		}
		else {
			User user  = users.get(0); //jer email mora biti jedinstven
			if(user.getPassword().equals(logger.getPassword())) {
				httpSession.setAttribute("user", user);
			} 
			else {
				return "PasswordError";
			}
		}
		return "OK";
	}

	@Override
	public User getLoggedInUser() {
		return (User) httpSession.getAttribute("user");
	}

	@Override
	public String updateUserInfo(User user) {
		
		if(!userRepository.findByEmail(user.getEmail()).isEmpty()) {
			return "EmailError";
		}
		
		User oldUser = userRepository.findOne(user.getId());
		oldUser.setName(user.getName());
		oldUser.setSurname(user.getSurname());
		oldUser.setEmail(user.getEmail());
		
		userRepository.save(oldUser);
		httpSession.setAttribute("user", oldUser);
		return "OK";
		
	}

	@Override
	public String updatePassword(User user) {
		User oldUser = userRepository.findOne(user.getId());
		oldUser.setPassword(user.getPassword());
		
		userRepository.save(oldUser);
		httpSession.setAttribute("user", oldUser);
		return "OK";
	}

	
	
	
	
	

}
