package com.example.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.model.FriendRequest;
import com.example.model.User;
import com.example.model.Visit;
import com.example.repository.FriendshipRepository;
import com.example.repository.UserRepository;
import com.example.repository.VisitRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	
	@Override
	public String createUser(User newUser) {
		if (userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
			String code = UUID.randomUUID().toString();
			newUser.setActivationCode(code);
			newUser.setActivated(true);
			//send(newUser.getEmail(), code);
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
			User user  = users.get(0);
			if(!user.isActivated()){
				return "EmailError";
			}
			
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
	public String activateAccount(User user) {
		String email = user.getEmail();
		String code = user.getActivationCode();
		User userDB = userRepository.findByEmail(email).get(0);
		
		if(userDB.getActivationCode().equals(code)){
			userDB.setActivated(true);
			userDB.setActivationCode("");
			userRepository.save(userDB);
			return "OK";
		}
		return "Error";
	}

	@Override
	public User getLoggedInUser() {
		return (User) httpSession.getAttribute("user");
	}

	@Override
	public String updateUserInfo(User user) {
		List<User> sameEmail = userRepository.findByEmail(user.getEmail());
		for (User u : sameEmail) {
			if(u.getId() != user.getId()) {
				return "EmailError";
			}
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
		List<FriendRequest> fr1 = friendshipRepository.findByReceiverIdAndSenderIdAndStatus(userId, friendId, "accepted");
		FriendRequest oldRequest = null;
		if(fr1.isEmpty()) {
			List<FriendRequest> fr2 = friendshipRepository.findByReceiverIdAndSenderIdAndStatus(friendId, userId, "accepted");
			if(!fr2.isEmpty()){
				oldRequest = fr2.get(0);
				friendshipRepository.delete(oldRequest);
			}
		}
		else {
			oldRequest = fr1.get(0);
			friendshipRepository.delete(oldRequest);
		}
		

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

	@Override
	public Collection<Visit> getAllVisits() {
		return visitRepository.findAll(null).getContent();
	}
	
	@Override
	public Collection<Visit> getVisitsForUser(){
		User user = (User)httpSession.getAttribute("user");
		return visitRepository.findByUserId(user.getId());
	}
	
	private void send(String emailAdress, String code) {
		
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo("anja.stef@gmail.com");
            helper.setReplyTo("someone@localhost");
            helper.setFrom("someone@localhost");
            helper.setSubject("Activation");
            helper.setText("http://localhost:8080/#/activateAccount/" + emailAdress+ "/" + code );
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
    }

	@Override
	public String loginFromInvitation(Long id) {
		User user = userRepository.findOne(id);
		if(user != null) {
			httpSession.setAttribute("user", user);
			return "OK";
		} else {
			return "Error";
		}
	}

	@Override
	public String setLatitudeAndLongitude(String latitude, String longitude) {
		try {
			double lat = Double.parseDouble(latitude);
			double lng = Double.parseDouble(longitude);
			User user = (User)httpSession.getAttribute("user");
			user.setLatitude(lat);
			user.setLongitude(lng);
			userRepository.save(user);
			return "OK";
		} catch (NumberFormatException e){
			return "LocationError";
		}
	}

	@Override
	public String logOut() {
		httpSession.invalidate();
		return "OK";
	}

}
