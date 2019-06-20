package com.univaq.eaglelibrary.dto;

import java.util.List;

import com.univaq.eaglelibrary.utility.Permission;

public class UserDTO {
	
	private Long id;
	private String username;
	private String firstName;
	private String secondName;
	private String email;
	private Enum<Permission> permission;
	private List<TranscriptionDTO> transcriptionList;
	
	
	//-- Getter and Setter --//
	
	public Enum<Permission> getPermission() {
		return permission;
	}
	public void setPermission(Enum<Permission> permission) {
		this.permission = permission;
	}
	public List<TranscriptionDTO> getTranscriptionList() {
		return transcriptionList;
	}
	public void setTranscriptionList(List<TranscriptionDTO> transcriptionList) {
		this.transcriptionList = transcriptionList;
	}
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
