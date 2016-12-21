package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.example.model.FriendRequest;

public interface FriendshipRepository extends Repository<FriendRequest, Long> {
	
	public FriendRequest save(FriendRequest entity);
	
	public void delete(FriendRequest entity);
	
	public List<FriendRequest> findBySenderIdAndStatusAllIgnoringCase(Long senderId, String status); 
	
	public List<FriendRequest> findByReceiverIdAndStatus(Long receiverId, String status); 
	
	public List<FriendRequest> findByReceiverIdAndSenderIdAndStatus(Long receiverId, Long senderId, String status);
	
	@Query("select f from FriendRequest f where f.status='accepted' and (f.senderId = :userId or f.receiverId = :userId)")
	public List<FriendRequest> findFriends(@Param("userId") Long userId); 
	
	@Query("select f from FriendRequest f where f.senderId = :userId or f.receiverId = :userId")
	public List<FriendRequest> findAllRequests(@Param("userId") Long userId);
}
