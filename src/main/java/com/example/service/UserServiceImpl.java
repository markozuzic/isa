package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.model.FriendRequest;
import com.example.model.User;
import com.example.repository.FriendshipRepository;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	
	
	@Override
	public String createUser(User newUser) {
		if (userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
			userRepository.save(newUser);
			return "OK";
		} 
		return "EmailError";
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

	@Override
	public Page<User> getAllUsers() {
		return userRepository.findAll(null);
	}

	@Override
	public String addFriend(Long id) {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		FriendRequest fr = new FriendRequest(userId, id, "pending");
		friendshipRepository.save(fr);
		return null;
	}

	@Override
	public ArrayList<User> getFriendsForUser() {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		List<FriendRequest> fr =  friendshipRepository.findFriends(userId);
		ArrayList<User> retval = new ArrayList<User>();
		for (FriendRequest friendRequest : fr) {
			if(userId.equals(friendRequest.getSenderId())) {
				retval.add(userRepository.findOne(friendRequest.getReceiverId()));
			} else {
				retval.add(userRepository.findOne(friendRequest.getSenderId()));
			}
		}
		return retval;
	}

	@Override
	public ArrayList<User> getPendingRequestsForUser() {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		List<FriendRequest> fr = friendshipRepository.findByReceiverIdAndStatus(userId, "pending");
		ArrayList<User> retval = new ArrayList<User>();
		for (FriendRequest friendRequest : fr) {
			retval.add(userRepository.findOne(friendRequest.getSenderId()));
			System.out.println(friendRequest.getSenderId());
		}
		return retval;
	}

	@Override
	public String acceptFriendRequest(Long friendId) {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		FriendRequest oldRequest = friendshipRepository.findByReceiverIdAndSenderIdAndStatus(userId, friendId, "pending").get(0);
		oldRequest.setStatus("accepted");
		friendshipRepository.save(oldRequest);
		
		return "OK";
	}

	@Override
	public String denyFriendRequest(Long friendId) {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		FriendRequest oldRequest = friendshipRepository.findByReceiverIdAndSenderIdAndStatus(userId, friendId, "pending").get(0);
		oldRequest.setStatus("denied");
		friendshipRepository.save(oldRequest);
		return "OK";
	}

	@Override
	public String removeFriend(Long friendId) {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		FriendRequest oldRequest = friendshipRepository.findByReceiverIdAndSenderIdAndStatus(userId, friendId, "accepted").get(0);
		friendshipRepository.delete(oldRequest);
		return "OK";
	}

	@Override
	public ArrayList<User> getFriendSuggestions() {
		Long userId = ((User)httpSession.getAttribute("user")).getId();
		List<FriendRequest> fr =  friendshipRepository.findAllRequests(userId);
		ArrayList<User> retVal = new ArrayList<User>();
		retVal.addAll(userRepository.findAll(null).getContent());
		
		for (FriendRequest friendRequest : fr) {
			//brisem ljude kojima je zahtev poslat
			if(userId.equals(friendRequest.getSenderId())) {
				User receiver = userRepository.findOne(friendRequest.getReceiverId());
				if(retVal.contains(receiver))
					retVal.remove(receiver);
			} 
			//brisem ljude kojima je poslao zahtev
			else if (userId.equals(friendRequest.getReceiverId())) {
				User sender = userRepository.findOne(friendRequest.getSenderId());
				if(retVal.contains(sender))
					retVal.remove(sender);
			}
		}
		//brisem njega samog
		retVal.remove(userRepository.findOne(userId));
		return retVal;
	}

	
	
	
	
	

}
