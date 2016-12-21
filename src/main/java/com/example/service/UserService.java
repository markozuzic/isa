package com.example.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.example.model.User;

public interface UserService {
	
	public String createUser(User newUser);
	
	public String logInUser(User user);
	
	public User getLoggedInUser();

	public String updateUserInfo(User user);

	public String updatePassword(User user);
	
	public Page<User> getAllUsers();

	public String addFriend(Long id);
	
	public ArrayList<User> getFriendsForUser();

	public ArrayList<User> getPendingRequestsForUser();

	public String acceptFriendRequest(Long id);

	public String denyFriendRequest(Long id);

	public String removeFriend(Long id);

	public ArrayList<User> getFriendSuggestions();

}
