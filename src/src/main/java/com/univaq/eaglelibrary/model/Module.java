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
@Table(name = "module")
public class Module {

	@Id
    @GeneratedValue
	private Long id;

	@Column(name = "code")
	private String code;
	
	@Column(name = "yearOfTheStudy")
	private Integer yearOfTheStudy;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "status")
	private String status;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	private User user;
	
	// setter and getter 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
