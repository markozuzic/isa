package com.example.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Page;

import com.example.model.Restaurant;
import com.example.model.User;
import com.example.model.Visit;

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
	
	public String createVisit(Restaurant r, Date d);

	public Collection<Visit> getAllVisits();
	
	public Collection<Visit> getVisitsForUser();

}
