package com.univaq.eaglelibrary.dto;

import java.util.Date;

public class ProfileDTO {

	private Long id;
	private String email;
	private Date dateOfBirth;
	private String matriculationNumber;
	private String address;
	private String degreeCourse;
	private UserDTO user;
	
	//-- Getter and Setter --//
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMatriculationNumber() {
		return matriculationNumber;
	}
	public void setMatriculationNumber(String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDegreeCourse() {
		return degreeCourse;
	}
	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
