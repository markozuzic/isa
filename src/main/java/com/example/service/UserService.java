package com.example.service;

import com.example.model.User;

public interface UserService {
	
	public void createUser(User newUser);
	
	public String logInUser(User user);
	
	public User getLoggedInUser();

	public String updateUserInfo(User user);

	public String updatePassword(User user);

}
