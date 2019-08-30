package com.univaq.eaglelibrary.dto;

import java.util.Date;

public class ModuleDTO {
	
	private Long id;
	private String code;
	private Integer yearOfTheStudy;
	private Date creationDate;
	private String comment;
	private String username;
	private String status;
	private UserDTO user;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getYearOfTheStudy() {
		return yearOfTheStudy;
	}
	public void setYearOfTheStudy(Integer yearOfTheStudy) {
		this.yearOfTheStudy = yearOfTheStudy;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
