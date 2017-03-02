package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public class SystemUser {
		
	@Id
	@GeneratedValue
	private long id;
	
	@Column()
	private String email;
	
	@Column()
	private String password;
	
	@Column()
	private String type;
	
	
	public SystemUser() {
		
	}

	public SystemUser(String email, String password, String type) {
		super();
		this.email = email;
		this.password = password;
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
