package com.univaq.eaglelibrary.dto;

import java.util.List;

public class TranscriptionDTO {

	private Long id;
	private String transcription;
	private String status;
	private List<UserDTO> userList;
	private PageDTO page;
	private Long lockedByuser;
	private String username;
	
	//-- Getter and Setter --//
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getLockedByuser() {
		return lockedByuser;
	}
	public void setLockedByuser(Long lockedByuser) {
		this.lockedByuser = lockedByuser;
	}
	public PageDTO getPage() {
		return page;
	}
	public void setPage(PageDTO page) {
		this.page = page;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTranscription() {
		return transcription;
	}
	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<UserDTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
 	
}
