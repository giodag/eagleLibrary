package com.univaq.eaglelibrary.dto;

public class UserDTO {
	
	private Long id;
	private String username;
	private String firstName;
	private String secondName;
	private String email;
	
	
	//-- Getter and Setter --//
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public String getEmailAddress() {
		return email;
	}
	public void setEmailAddress(String emailAddress) {
		this.email = emailAddress;
	}

}
