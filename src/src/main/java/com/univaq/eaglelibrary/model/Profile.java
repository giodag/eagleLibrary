package com.univaq.eaglelibrary.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
	
	@Id
    @GeneratedValue
	private Long id;

	@Column(name = "dateOfBirth")
	private Date dateOfBirth;
	
	@Column(name = "address")
	private String address;

	@Column(name = "matriculationNumber")
	private String matriculationNumber;
	
	@Column(name = "degreeCourse")
	private String degreeCourse;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	private User user;
	
	// getter and setter 
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMatriculationNumber() {
		return matriculationNumber;
	}

	public void setMatriculationNumber(String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}

	public String getDegreeCourse() {
		return degreeCourse;
	}

	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}
}
