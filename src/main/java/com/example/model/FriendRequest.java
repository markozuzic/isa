package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class FriendRequest {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private long senderId;
	
	@Column(nullable = false)
	private long receiverId;
	
	@Column(nullable = false)
	private String status; //accepted, denied, pending

	public FriendRequest(){}
	
	public FriendRequest(long senderId, long receiverId, String status) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
	}
	
	
	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
